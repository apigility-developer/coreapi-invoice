package com.gravitant.product.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorDto implements Serializable {
	
	private static final long serialVersionUID = 3225011787024978865L;
	
	private ErrorCode error;
	private String message;
	private String details;
	private List<FieldErrorDto> fields;
	
	public ErrorDto() {
	}

	public ErrorCode getError() {
		return this.error;
	}
	
	public void setError(ErrorCode error) {
		this.error = error;
	}
	
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return this.details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}

	public List<FieldErrorDto> getFields() {
		if (this.fields == null) {
			this.fields = new ArrayList<FieldErrorDto> ();
		}
		return this.fields;
	}

	public void setFields(List<FieldErrorDto> fields) {
		this.fields = fields;
	}
	
	public void addFieldError(String field, String error) {
		this.getFields().add(new FieldErrorDto(field, error));
	}

}
