package com.miu.security;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Arrays;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miu.config.JHipsterProperties;
import com.miu.domain.PersistentToken;
import com.miu.repository.PersistentTokenRepository;
import com.miu.repository.UserRepository;

/**
 * Custom implementation of Spring Security's RememberMeServices.
 * <p>
 * Persistent tokens are used by Spring Security to automatically log in users.
 * <p>
 * This is a specific implementation of Spring Security's remember-me
 * authentication, but it is much more powerful than the standard
 * implementations:
 * <ul>
 * <li>It allows a user to see the list of his currently opened sessions, and
 * invalidate them</li>
 * <li>It stores more information, such as the IP address and the user agent,
 * for audit purposes
 * <li>
 * <li>When a user logs out, only his current session is invalidated, and not
 * all of his sessions</li>
 * </ul>
 * <p>
 * This is inspired by:
 * <ul>
 * <li><a href=
 * "http://jaspan.com/improved_persistent_login_cookie_best_practice">Improved
 * Persistent Login Cookie Best Practice</a></li>
 * <li><a href=
 * "https://github.com/blog/1661-modeling-your-app-s-user-session">Github's
 * "Modeling your App's User Session"</a></li>
 * </ul>
 * <p>
 * The main algorithm comes from Spring Security's
 * PersistentTokenBasedRememberMeServices, but this class couldn't be cleanly
 * extended.
 */
@Service
public class CustomPersistentRememberMeServices extends AbstractRememberMeServices {

	private static final int DEFAULT_SERIES_LENGTH = 16;

	private static final int DEFAULT_TOKEN_LENGTH = 16;

	// Token is valid for one month
	private static final int TOKEN_VALIDITY_DAYS = 31;

	private static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * TOKEN_VALIDITY_DAYS;

	private final Logger LOGGER = LoggerFactory.getLogger(CustomPersistentRememberMeServices.class);

	@Inject
	private PersistentTokenRepository persistentTokenRepository;

	private SecureRandom random;

	@Inject
	private UserRepository userRepository;

	@Inject
	public CustomPersistentRememberMeServices(JHipsterProperties jHipsterProperties,
			org.springframework.security.core.userdetails.UserDetailsService userDetailsService) {

		super(jHipsterProperties.getSecurity().getRememberMe().getKey(), userDetailsService);
		random = new SecureRandom();
	}

	private void addCookie(PersistentToken token, HttpServletRequest request, HttpServletResponse response) {
		setCookie(new String[] { token.getSeries(), token.getTokenValue() }, TOKEN_VALIDITY_SECONDS, request, response);
	}

	private String generateSeriesData() {
		byte[] newSeries = new byte[DEFAULT_SERIES_LENGTH];
		random.nextBytes(newSeries);
		return new String(Base64.encode(newSeries));
	}

	private String generateTokenData() {
		byte[] newToken = new byte[DEFAULT_TOKEN_LENGTH];
		random.nextBytes(newToken);
		return new String(Base64.encode(newToken));
	}

	/**
	 * Validate the token and return it.
	 */
	private PersistentToken getPersistentToken(String[] cookieTokens) {
		if (cookieTokens.length != 2) {
			throw new InvalidCookieException("Cookie token did not contain " + 2 + " tokens, but contained '"
					+ Arrays.asList(cookieTokens) + "'");
		}

		String presentedSeries = cookieTokens[0];
		String presentedToken = cookieTokens[1];
		PersistentToken token = persistentTokenRepository.findOne(presentedSeries);

		if (token == null) {
			// No series match, so we can't authenticate using this cookie
			throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);
		}

		// We have a match for this user/series combination
		LOGGER.info("presentedToken={} / tokenValue={}", presentedToken, token.getTokenValue());

		if (!presentedToken.equals(token.getTokenValue())) {
			// Token doesn't match series value. Delete this session and throw
			// an exception.
			persistentTokenRepository.delete(token);
			throw new CookieTheftException(
					"Invalid remember-me token (Series/token) mismatch. Implies previous " + "cookie theft attack.");
		}

		if (token.getTokenDate().plusDays(TOKEN_VALIDITY_DAYS).isBefore(LocalDate.now())) {
			persistentTokenRepository.delete(token);
			throw new RememberMeAuthenticationException("Remember-me login has expired");
		}

		return token;
	}

	/**
	 * When logout occurs, only invalidate the current token, and not all user
	 * sessions.
	 * <p>
	 * The standard Spring Security implementations are too basic: they
	 * invalidate all tokens for the current user, so when he logs out from one
	 * browser, all his other sessions are destroyed.
	 */
	@Override
	@Transactional
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String rememberMeCookie = extractRememberMeCookie(request);
		if (rememberMeCookie != null && rememberMeCookie.length() != 0) {
			try {
				String[] cookieTokens = decodeCookie(rememberMeCookie);
				PersistentToken token = getPersistentToken(cookieTokens);
				persistentTokenRepository.delete(token);
			}

			catch (InvalidCookieException ice) {
				LOGGER.info("Invalid cookie, no persistent token could be deleted", ice);
			}

			catch (RememberMeAuthenticationException rmae) {
				LOGGER.debug("No persistent token found, so no token could be deleted", rmae);
			}
		}

		super.logout(request, response, authentication);
	}

	@Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {

		String login = successfulAuthentication.getName();

		LOGGER.debug("Creating new persistent login for user {}", login);

		PersistentToken token = userRepository.findOneByLogin(login).map(u -> {
			PersistentToken t = new PersistentToken();
			t.setSeries(generateSeriesData());
			t.setUser(u);
			t.setTokenValue(generateTokenData());
			t.setTokenDate(LocalDate.now());
			t.setIpAddress(request.getRemoteAddr());
			t.setUserAgent(request.getHeader("User-Agent"));
			return t;
		}).orElseThrow(() -> new UsernameNotFoundException("User " + login + " was not found in the database"));

		try {
			persistentTokenRepository.saveAndFlush(token);
			addCookie(token, request, response);
		}

		catch (DataAccessException e) {
			LOGGER.error("Failed to save persistent token ", e);
		}
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
			HttpServletResponse response) {

		PersistentToken token = getPersistentToken(cookieTokens);
		String login = token.getUser().getLogin();

		// Token also matches, so login is valid. Update the token value,
		// keeping the *same* series number.
		LOGGER.debug("Refreshing persistent login token for user '{}', series '{}'", login, token.getSeries());
		token.setTokenDate(LocalDate.now());
		token.setTokenValue(generateTokenData());
		token.setIpAddress(request.getRemoteAddr());
		token.setUserAgent(request.getHeader("User-Agent"));

		try {
			persistentTokenRepository.saveAndFlush(token);
			addCookie(token, request, response);
		}

		catch (DataAccessException e) {
			LOGGER.error("Failed to update token: ", e);
			throw new RememberMeAuthenticationException("Autologin failed due to data access problem", e);
		}

		return getUserDetailsService().loadUserByUsername(login);
	}
}
