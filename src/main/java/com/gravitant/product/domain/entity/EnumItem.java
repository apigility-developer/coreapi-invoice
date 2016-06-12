package com.gravitant.product.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EnumItem implements Serializable {
	
	private static final long serialVersionUID = -1L;
	
	@Id
	@Column(name="CODE")
	private String code;
	
	@Column(name="DISPLAY_LABEL")
	private String label;
	
	public EnumItem() {
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
