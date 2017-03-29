package com.miu.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.miu.config.JHipsterProperties;
import com.miu.domain.OnlineApplication;
import com.miu.domain.User;

/**
 * Service for sending e-mails.
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

	private static final String BASE_URL = "baseUrl";

	private static final String ONLINE_APPLICATION = "onlineApplication";

	private static final String USER = "user";

	@Inject
	private JavaMailSenderImpl javaMailSender;

	@Inject
	private JHipsterProperties jHipsterProperties;

	private final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

	@Inject
	private MessageSource messageSource;

	@Inject
	private SpringTemplateEngine templateEngine;

	@Inject
	private UserService userService;

	@Async
	public void sendActivationEmail(User user) {
		LOGGER.debug("Sending activation e-mail to '{}'", user.getEmail());
		Locale locale = Locale.forLanguageTag(user.getLangKey());
		Context context = new Context(locale);
		context.setVariable(USER, user);
		context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
		String content = templateEngine.process("activationEmail", context);
		String subject = messageSource.getMessage("email.activation.title", null, locale);
		sendEmail(user.getEmail(), subject, content, false, true);
	}

	@Async
	public void sendCreationEmail(User user) {
		LOGGER.debug("Sending creation e-mail to '{}'", user.getEmail());
		Locale locale = Locale.forLanguageTag(user.getLangKey());
		Context context = new Context(locale);
		context.setVariable(USER, user);
		context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
		String content = templateEngine.process("creationEmail", context);
		String subject = messageSource.getMessage("email.activation.title", null, locale);
		sendEmail(user.getEmail(), subject, content, false, true);
	}

	@Async
	public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
		LOGGER.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}", isMultipart,
				isHtml, to, subject, content);

		// Prepare message using a Spring helper
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
			message.setTo(to);
			message.setFrom(jHipsterProperties.getMail().getFrom());
			message.setSubject(subject);
			message.setText(content, isHtml);
			javaMailSender.send(mimeMessage);
			LOGGER.debug("Sent e-mail to User '{}'", to);
		}

		catch (Exception e) {
			LOGGER.warn("E-mail could not be sent to user '{}'", to, e);
		}
	}

	@Async
	public void sendOnlineApplicationEmail(OnlineApplication onlineApplication) {
		User user = userService.getAdminUser();
		LOGGER.debug("Sending Online Application e-mail to '{}'", user.getEmail());
		Locale locale = Locale.forLanguageTag(user.getLangKey());
		Context context = new Context(locale);
		context.setVariable(USER, user);
		context.setVariable(ONLINE_APPLICATION, onlineApplication);
		context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
		String content = templateEngine.process("onlineApplicationEmail", context);
		String subject = messageSource.getMessage("email.activation.title", null, locale);
		sendEmail(user.getEmail(), subject, content, false, true);
	}

	@Async
	public void sendPasswordResetMail(User user) {
		LOGGER.debug("Sending password reset e-mail to '{}'", user.getEmail());
		Locale locale = Locale.forLanguageTag(user.getLangKey());
		Context context = new Context(locale);
		context.setVariable(USER, user);
		context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
		String content = templateEngine.process("passwordResetEmail", context);
		String subject = messageSource.getMessage("email.reset.title", null, locale);
		sendEmail(user.getEmail(), subject, content, false, true);
	}
}
