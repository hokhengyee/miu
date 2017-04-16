package com.miu.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miu.config.Constants;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(nullable = false)
	private boolean activated = false;

	@Size(max = 20)
	@Column(name = "activation_key", length = 20)
	@JsonIgnore
	private String activationKey;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "jhi_user_authority", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_name", referencedColumnName = "name") })
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Authority> authorities = new HashSet<>();

	@Email
	@Size(max = 100)
	@Column(length = 100, unique = true)
	private String email;

	@Size(max = 50)
	@Column(name = "first_name", length = 50)
	private String firstName;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 2, max = 5)
	@Column(name = "lang_key", length = 5)
	private String langKey;

	@Size(max = 50)
	@Column(name = "last_name", length = 50)
	private String lastName;

	@NotNull
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	@Column(length = 50, unique = true, nullable = false)
	private String login;

	@JsonIgnore
	@NotNull
	@Size(min = 60, max = 60)
	@Column(name = "password_hash", length = 60)
	private String password;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<PersistentToken> persistentTokens = new HashSet<>();

	@Column(name = "reset_date", nullable = true)
	private ZonedDateTime resetDate = null;

	@Size(max = 20)
	@Column(name = "reset_key", length = 20)
	private String resetKey;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;

        return login.equals(user.login);
    }

	public boolean getActivated() {
		return activated;
	}

	public String getActivationKey() {
		return activationKey;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public Long getId() {
		return id;
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

	public String getPassword() {
		return password;
	}

	public Set<PersistentToken> getPersistentTokens() {
		return persistentTokens;
	}

	public ZonedDateTime getResetDate() {
		return resetDate;
	}

	public String getResetKey() {
		return resetKey;
	}

	@Override
	public int hashCode() {
		return login.hashCode();
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// Lowercase the login before saving it in database
	public void setLogin(String login) {
		this.login = login.toLowerCase(Locale.ENGLISH);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPersistentTokens(Set<PersistentToken> persistentTokens) {
		this.persistentTokens = persistentTokens;
	}

	public void setResetDate(ZonedDateTime resetDate) {
		this.resetDate = resetDate;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

	@Override
	public String toString() {
		return "User{" + "login='" + login + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
				+ '\'' + ", email='" + email + '\'' + ", activated='" + activated + '\'' + ", langKey='" + langKey
				+ '\'' + ", activationKey='" + activationKey + '\'' + "}";
	}
}
