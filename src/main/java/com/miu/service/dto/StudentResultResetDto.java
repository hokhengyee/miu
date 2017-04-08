package com.miu.service.dto;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.miu.domain.User;

public class StudentResultResetDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@NotNull
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
