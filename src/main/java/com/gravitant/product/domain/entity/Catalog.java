package com.gravitant.product.domain.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="CATALOG")
public class Catalog implements Serializable {
	
	private static final long serialVersionUID = -1L;

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="NAME")
	private String name;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="DISPLAY_NAME")
	private String displayName;



	public Catalog() {
	}
	
	public String getId() {
		return  this.id;
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



	public boolean equals(Catalog obj) {
		 if (obj == null) {
			 return false;
		 }
		 if (!getClass().isInstance(obj)) {
			 return false;
		 }
		
		 return Objects.equals(getId(), obj.getId());
	}
		  
	public int hashCode() {
		 return Objects.hashCode(getId());
	}
}
