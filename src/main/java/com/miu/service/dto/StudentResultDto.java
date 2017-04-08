package com.miu.service.dto;

import java.io.Serializable;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.miu.domain.User;

public class StudentResultDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Lob
	@NotNull
	private byte[] studentResultTemplate;

	private String studentResultTemplateContentType;

	@ManyToOne
	@NotNull
	private User user;

	public byte[] getStudentResultTemplate() {
		return studentResultTemplate;
	}

	public String getStudentResultTemplateContentType() {
		return studentResultTemplateContentType;
	}

	public User getUser() {
		return user;
	}

	public void setStudentResultTemplate(byte[] studentResultTemplate) {
		this.studentResultTemplate = studentResultTemplate;
	}

	public void setStudentResultTemplateContentType(String studentResultTemplateContentType) {
		this.studentResultTemplateContentType = studentResultTemplateContentType;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
