package com.gravitant.product.service.dto;

public abstract class DtoException extends RuntimeException {
	
	private static final long serialVersionUID = 2741718638609514681L;

	private String name;
	private String[] fields;

	public DtoException(Class<?> dtoType, String... fields) {
		this(dtoType.getSimpleName(), fields);
	}
	
	public DtoException(String name, String... fields) {
		this.fields = fields;
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String[] getFields() {
		return this.fields;
	}
	
	protected abstract ErrorCode getCode();
}
