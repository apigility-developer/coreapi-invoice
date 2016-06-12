package com.gravitant.product.service.dto;

import java.io.Serializable;

public class FieldErrorDto implements Serializable {
	
	private static final long serialVersionUID = 3513670228507145684L;
	
	private String field;
	private String text;
	
	public FieldErrorDto(String field, String text) {
		this.text = text;
		this.field = field;
	}

	public String getField() {
		return this.field;
	}
	
	public String getText() {
		return text;
	}

}
