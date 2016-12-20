package com.miu.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Persistent tokens are used by Spring Security to automatically log in users.
 *
 * @see com.miu.security.CustomPersistentRememberMeServices
 */
@Entity
@Table(name = "jhi_persistent_token")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersistentToken implements Serializable {

	private static final int MAX_USER_AGENT_LEN = 255;

	private static final long serialVersionUID = 1L;

	// an IPV6 address max length is 39 characters
	@Size(min = 0, max = 39)
	@Column(name = "ip_address", length = 39)
	private String ipAddress;

	@Id
	private String series;

	@Column(name = "token_date")
	private LocalDate tokenDate;

	@JsonIgnore
	@NotNull
	@Column(name = "token_value", nullable = false)
	private String tokenValue;

	@JsonIgnore
	@ManyToOne
	private User user;

	@Column(name = "user_agent")
	private String userAgent;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		PersistentToken that = (PersistentToken) o;

		if (!series.equals(that.series)) {
			return false;
		}

		return true;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getSeries() {
		return series;
	}

	public LocalDate getTokenDate() {
		return tokenDate;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public User getUser() {
		return user;
	}

	public String getUserAgent() {
		return userAgent;
	}

	@Override
	public int hashCode() {
		return series.hashCode();
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setTokenDate(LocalDate tokenDate) {
		this.tokenDate = tokenDate;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserAgent(String userAgent) {
		if (userAgent.length() >= MAX_USER_AGENT_LEN) {
			this.userAgent = userAgent.substring(0, MAX_USER_AGENT_LEN - 1);
		} else {
			this.userAgent = userAgent;
		}
	}

	@Override
	public String toString() {
		return "PersistentToken{" + "series='" + series + '\'' + ", tokenValue='" + tokenValue + '\'' + ", tokenDate="
				+ tokenDate + ", ipAddress='" + ipAddress + '\'' + ", userAgent='" + userAgent + '\'' + "}";
	}
}
