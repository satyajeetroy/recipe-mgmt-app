package com.roys.recipemgmt.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.roys.recipemgmt.dto.ErrorResponse;
import com.roys.recipemgmt.dto.FieldError;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * Exception handler class for handling all types of Error and Exception. One
 * can provide various handler methods within this class for handling variety of
 * exceptions.
 * 
 * @author Satyajeet Roy
 *
 */
@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

	/**
	 * Handles all Exceptions associated with HTTP response codes.
	 * 
	 * @param exception
	 * @return {@link ErrorResponse}
	 */
	@ExceptionHandler(ResponseStatusException.class)
	@ApiResponse(responseCode = "4xx/5xx", description = "Error")
	public ResponseEntity<ErrorResponse> handleNotFound(final ResponseStatusException exception) {
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(exception.getStatus().value());
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getMessage());
		return new ResponseEntity<>(errorResponse, exception.getStatus());
	}

	/**
	 * Handles exceptions when validation on an argument annotated with @Valid
	 * fails.
	 * 
	 * @param exception
	 * @return {@link ErrorResponse}
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
		final BindingResult bindingResult = exception.getBindingResult();
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors().stream().map(error -> {
			final FieldError fieldError = new FieldError();
			fieldError.setErrorCode(error.getCode());
			fieldError.setField(error.getField());
			return fieldError;
		}).collect(Collectors.toList());
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setFieldErrors(fieldErrors);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles all errors and exceptions encountered by the application
	 * 
	 * @param exception
	 * @return {@link ErrorResponse}
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ErrorResponse> handleThrowable(final Throwable exception) {
		exception.printStackTrace();
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getCause().toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handles HttpMessage read related errors
	 * 
	 * @param exception
	 * @return
	 */
	@ApiResponse(responseCode = "400", description = "Bad Request")
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception) {
		exception.printStackTrace();
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getCause().toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}