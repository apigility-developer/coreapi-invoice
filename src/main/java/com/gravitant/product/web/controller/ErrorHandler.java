package com.gravitant.product.web.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.gravitant.product.service.dto.DtoNotFoundException;
import com.gravitant.product.service.dto.DtoNotUniqueException;
import com.gravitant.product.service.dto.ErrorCode;
import com.gravitant.product.service.dto.ErrorDto;

@ControllerAdvice
public class ErrorHandler {

	@Inject private MessageSource messageSource;
	
	public ErrorHandler() {
	}
	
	@ExceptionHandler({DtoNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorDto handleNotFound(DtoNotFoundException notfoundex) {
		String errorCode = notfoundex.getName() + "." + notfoundex.getCode();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError(notfoundex.getCode());
		errorDto.setMessage(this.getText(errorCode, (Object[]) notfoundex.getFields()));
		return errorDto;
	}
	
	@ExceptionHandler({DtoNotUniqueException.class})
	@ResponseStatus(HttpStatus.CONFLICT)
	public @ResponseBody ErrorDto handleNotUnique(DtoNotUniqueException notuniqueex) {
		String errorCode = notuniqueex.getName() + "." + notuniqueex.getCode();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError(notuniqueex.getCode());
		errorDto.setMessage(this.getText(errorCode, (Object[]) notuniqueex.getFields()));
		return errorDto;
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorDto handleNotValid(MethodArgumentNotValidException notvalidex) {
		String fieldname = null;
		String fieldErrorCode = null;
		String validationMessage = null;
		ErrorCode errorCode = ErrorCode.NotValid;
		BindingResult validationResult = notvalidex.getBindingResult();
		List<FieldError> fieldErrors = validationResult.getFieldErrors();
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError(errorCode);
		errorDto.setMessage(this.getText(errorCode));
		
		for (FieldError currentFieldError : fieldErrors) {
			fieldErrorCode = String.format("%s.%s.%s", currentFieldError.getCode(), currentFieldError.getObjectName(), currentFieldError.getField());
			validationMessage = this.getText(fieldErrorCode, currentFieldError.getArguments());;
			fieldname = currentFieldError.getField();
			errorDto.addFieldError(fieldname, validationMessage);
		}
		return errorDto;
	}
	
	@ExceptionHandler({RuntimeException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorDto handleInternalServer(Exception exception) {
		ErrorCode errorCode = ErrorCode.InternalError;
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError(errorCode);
		errorDto.setMessage(this.getText(errorCode));
		exception.printStackTrace();
		return errorDto;
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorDto handleMessageNotReadableException(HttpMessageNotReadableException messagenotreadableex) {
		ErrorDto errorDto = null;
		String unrecognizedPropertyName = null;
		ErrorCode errorCode = ErrorCode.UnrecognizedProperty;
		Throwable mostSpecificCause = messagenotreadableex.getMostSpecificCause();
		UnrecognizedPropertyException unrecognizedPropertyException = null;
		
		if (mostSpecificCause != null) {
			if (mostSpecificCause instanceof UnrecognizedPropertyException) {
				unrecognizedPropertyException = (UnrecognizedPropertyException) mostSpecificCause;
				unrecognizedPropertyName = unrecognizedPropertyException.getPropertyName();
				errorDto = new ErrorDto();
				errorDto.setError(errorCode);
				errorDto.setMessage(this.getText(errorCode, unrecognizedPropertyName));
			} else {
				errorDto = new ErrorDto();
				errorDto.setError(ErrorCode.UnreadableMessage);
				errorDto.setMessage(messagenotreadableex.getMessage());
			}
		}
		return errorDto;
	}
	
	private String getText(ErrorCode errorCode, Object... arguments) {
		return this.getText(errorCode.name(), arguments);
	}
	
	private String getText(String error, Object...arguments) {
		return this.messageSource.getMessage(error, arguments, LocaleContextHolder.getLocale());
	}
	
}
