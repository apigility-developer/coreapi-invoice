package com.gravitant.product.service.dto;

import java.io.Serializable;

public abstract class EnumDto implements Serializable {
	
	private static final long serialVersionUID = -1L;
	
	private String code;
	private String label;
	
	public EnumDto() {
	}

	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

}
