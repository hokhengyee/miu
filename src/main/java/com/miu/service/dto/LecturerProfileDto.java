package com.miu.service.dto;

import java.io.Serializable;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.miu.domain.User;

public class LecturerProfileDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Lob
	@NotNull
	private byte[] lecturerProfileTemplate;

	private String lecturerProfileTemplateContentType;

	@ManyToOne
	@NotNull
	private User user;

	public byte[] getLecturerProfileTemplate() {
		return lecturerProfileTemplate;
	}

	public String getLecturerProfileTemplateContentType() {
		return lecturerProfileTemplateContentType;
	}

	public User getUser() {
		return user;
	}

	public void setLecturerProfileTemplate(byte[] lecturerProfileTemplate) {
		this.lecturerProfileTemplate = lecturerProfileTemplate;
	}

	public void setLecturerProfileTemplateContentType(String lecturerProfileTemplateContentType) {
		this.lecturerProfileTemplateContentType = lecturerProfileTemplateContentType;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
