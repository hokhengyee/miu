package com.miu.service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miu.domain.Authority;
import com.miu.domain.User;
import com.miu.repository.AuthorityRepository;
import com.miu.repository.PersistentTokenRepository;
import com.miu.repository.UserRepository;
import com.miu.security.AuthoritiesConstants;
import com.miu.security.SecurityUtils;
import com.miu.service.util.RandomUtil;
import com.miu.web.rest.vm.ManagedUserVM;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

	@Inject
	private AuthorityRepository authorityRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Inject
	private PasswordEncoder passwordEncoder;

	@Inject
	private PersistentTokenRepository persistentTokenRepository;

	@Inject
	private UserRepository userRepository;

	public Optional<User> activateRegistration(String key) {
		LOGGER.debug("Activating user for activation key {}", key);
		return userRepository.findOneByActivationKey(key).map(user -> {
			// activate given user for the registration key.
			user.setActivated(true);
			user.setActivationKey(null);
			LOGGER.debug("Activated user: {}", user);
			return user;
		});
	}

	public void changePassword(String password) {
		userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
			String encryptedPassword = passwordEncoder.encode(password);
			user.setPassword(encryptedPassword);
			LOGGER.debug("Changed password for User: {}", user);
		});
	}

	public Optional<User> completePasswordReset(String newPassword, String key) {
		LOGGER.debug("Reset user password for reset key {}", key);

		return userRepository.findOneByResetKey(key).filter(user -> {
			ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
			return user.getResetDate().isAfter(oneDayAgo);
		}).map(user -> {
			user.setPassword(passwordEncoder.encode(newPassword));
			user.setResetKey(null);
			user.setResetDate(null);
			return user;
		});
	}

	public User createUser(ManagedUserVM managedUserVM) {
		User user = new User();
		user.setLogin(managedUserVM.getLogin());
		user.setFirstName(managedUserVM.getFirstName());
		user.setLastName(managedUserVM.getLastName());
		user.setEmail(managedUserVM.getEmail());
		if (managedUserVM.getLangKey() == null) {
			user.setLangKey("en"); // default language
		} else {
			user.setLangKey(managedUserVM.getLangKey());
		}
		if (managedUserVM.getAuthorities() != null) {
			Set<Authority> authorities = new HashSet<>();
			managedUserVM.getAuthorities()
					.forEach(authority -> authorities.add(authorityRepository.findOne(authority)));
			user.setAuthorities(authorities);
		}
		String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
		user.setPassword(encryptedPassword);
		user.setResetKey(RandomUtil.generateResetKey());
		user.setResetDate(ZonedDateTime.now());
		user.setActivated(true);
		userRepository.save(user);
		LOGGER.debug("Created Information for User: {}", user);
		return user;
	}

	public User createUser(String login, String password, String firstName, String lastName, String email,
			String langKey) {

		User newUser = new User();
		Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
		Set<Authority> authorities = new HashSet<>();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setLogin(login);
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmail(email);
		newUser.setLangKey(langKey);
		// new user is not active
		newUser.setActivated(false);
		// new user gets registration key
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		authorities.add(authority);
		newUser.setAuthorities(authorities);
		userRepository.save(newUser);
		LOGGER.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	public void deleteUser(String login) {
		userRepository.findOneByLogin(login).ifPresent(user -> {
			userRepository.delete(user);
			LOGGER.debug("Deleted User: {}", user);
		});
	}

	@Transactional(readOnly = true)
	public User getAdminUser() {
		return userRepository.findAdminUser();
	}

	@Transactional(readOnly = true)
	public User getUserWithAuthorities() {
		Optional<User> optionalUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
		User user = null;
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
			user.getAuthorities().size(); // eagerly load the association
		}
		return user;
	}

	@Transactional(readOnly = true)
	public User getUserWithAuthorities(Long id) {
		User user = userRepository.findOne(id);
		user.getAuthorities().size(); // eagerly load the association
		return user;
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthoritiesByLogin(String login) {
		return userRepository.findOneByLogin(login).map(user -> {
			user.getAuthorities().size();
			return user;
		});
	}

	/**
	 * Not activated users should be automatically deleted after 3 days.
	 * <p>
	 * This is scheduled to get fired everyday, at 01:00 (am).
	 * </p>
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void removeNotActivatedUsers() {
		ZonedDateTime now = ZonedDateTime.now();
		List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
		for (User user : users) {
			LOGGER.debug("Deleting not activated user {}", user.getLogin());
			userRepository.delete(user);
		}
	}

	/**
	 * Persistent Token are used for providing automatic authentication, they
	 * should be automatically deleted after 30 days.
	 * <p>
	 * This is scheduled to get fired everyday, at midnight.
	 * </p>
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void removeOldPersistentTokens() {
		LocalDate now = LocalDate.now();
		persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).forEach(token -> {
			LOGGER.debug("Deleting token {}", token.getSeries());
			User user = token.getUser();
			user.getPersistentTokens().remove(token);
			persistentTokenRepository.delete(token);
		});
	}

	public Optional<User> requestPasswordReset(String mail) {
		return userRepository.findOneByEmail(mail).filter(User::getActivated).map(user -> {
			user.setResetKey(RandomUtil.generateResetKey());
			user.setResetDate(ZonedDateTime.now());
			return user;
		});
	}

	public void updateUser(Long id, String login, String firstName, String lastName, String email, boolean activated,
			String langKey, Set<String> authorities) {

		Optional.of(userRepository.findOne(id)).ifPresent(user -> {
			user.setLogin(login);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setActivated(activated);
			user.setLangKey(langKey);
			Set<Authority> managedAuthorities = user.getAuthorities();
			managedAuthorities.clear();
			authorities.forEach(authority -> managedAuthorities.add(authorityRepository.findOne(authority)));
			LOGGER.debug("Changed Information for User: {}", user);
		});
	}

	public void updateUser(String firstName, String lastName, String email, String langKey) {
		userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setLangKey(langKey);
			LOGGER.debug("Changed Information for User: {}", user);
		});
	}
}
