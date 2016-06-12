package com.gravitant.product.web.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import org.joda.time.DateTime;

@Relation(value="catalog", collectionRelation="catalogs")
public class CatalogResource extends ResourceSupport {
	
	private String displayName;
	private String description;
	private String name;
	public CatalogResource() {
	}

	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
