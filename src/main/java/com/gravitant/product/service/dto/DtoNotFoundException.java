package com.gravitant.product.service.dto;

public class DtoNotFoundException extends DtoException {
	
	private static final long serialVersionUID = 2741718638609514681L;

	public DtoNotFoundException(Class<?> dtoType, String... fields) {
		super(dtoType, fields);
	}
	
	public DtoNotFoundException(String name, String... fields) {
		super(name, fields);
	}
	
	public ErrorCode getCode() {
		return ErrorCode.NotFound;
	}

}
