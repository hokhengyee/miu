package com.miu.service.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class ForumMessageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long courseID;

	@NotNull
	private String message;

	public Long getCourseID() {
		return courseID;
	}

	public String getMessage() {
		return message;
	}

	public void setCourseID(Long courseID) {
		this.courseID = courseID;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
