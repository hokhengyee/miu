package com.miu.service.dto;

import java.io.Serializable;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

public class ROCDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Lob
	@NotNull
	private byte[] rocTemplate;

	private String rocTemplateContentType;

	public byte[] getRocTemplate() {
		return rocTemplate;
	}

	public String getRocTemplateContentType() {
		return rocTemplateContentType;
	}

	public void setRocTemplate(byte[] rocTemplate) {
		this.rocTemplate = rocTemplate;
	}

	public void setRocTemplateContentType(String rocTemplateContentType) {
		this.rocTemplateContentType = rocTemplateContentType;
	}

}
