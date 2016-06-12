package com.gravitant.product.service.dto;

import java.io.Serializable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "_links" })
public class CatalogDto implements Serializable {

	private static final long serialVersionUID = -1L;

	private String id;

	@NotEmpty
	@Size(max=25)
	private String name;

	@NotEmpty
	@Size(max=200)
	private String description;

	@NotEmpty
	@Size(max=50)
	private String displayName;
	public CatalogDto() {
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
