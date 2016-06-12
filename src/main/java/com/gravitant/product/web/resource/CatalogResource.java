package com.gravitant.product.web.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import org.joda.time.DateTime;

@Relation(value="catalog", collectionRelation="catalogs")
public class CatalogResource extends ResourceSupport {
	
	private String name;
	private String description;
	private String displayName;
	public CatalogResource() {
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
