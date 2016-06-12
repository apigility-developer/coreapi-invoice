package com.gravitant.product.service.dto;

public class DtoNotUniqueException extends DtoException {
	
	private static final long serialVersionUID = 9186225774476136171L;

	public DtoNotUniqueException(Class<?> dtoType, String... fields) {
		super(dtoType.getSimpleName(), fields);
	}
	
	public DtoNotUniqueException(String name, String... fields) {
		super(name, fields);
	}
	
	public ErrorCode getCode() {
		return ErrorCode.Duplicate;
	}
}
