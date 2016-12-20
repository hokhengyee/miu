package com.miu.service.dto;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.miu.config.Constants;
import com.miu.domain.Authority;
import com.miu.domain.User;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

	private boolean activated = false;

	private Set<String> authorities;

	@Email
	@Size(min = 5, max = 100)
	private String email;

	@Size(max = 50)
	private String firstName;

	@Size(min = 2, max = 5)
	private String langKey;

	@Size(max = 50)
	private String lastName;

	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	private String login;

	public UserDTO() {
	}

	public UserDTO(String login, String firstName, String lastName, String email, boolean activated, String langKey,
			Set<String> authorities) {

		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.activated = activated;
		this.langKey = langKey;
		this.authorities = authorities;
	}

	public UserDTO(User user) {
		this(user.getLogin(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getActivated(),
				user.getLangKey(), user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLangKey() {
		return langKey;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLogin() {
		return login;
	}

	public boolean isActivated() {
		return activated;
	}

	@Override
	public String toString() {
		return "UserDTO{" + "login='" + login + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
				+ '\'' + ", email='" + email + '\'' + ", activated=" + activated + ", langKey='" + langKey + '\''
				+ ", authorities=" + authorities + "}";
	}
}
