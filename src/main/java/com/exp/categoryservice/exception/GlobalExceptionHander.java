package com.exp.categoryservice.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHander {

	private static final String failedStatus = "Failed";

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> systemException(Exception ex) {

		ExceptionResponse res = new ExceptionResponse(failedStatus, ex.getMessage());

		return new ResponseEntity<ExceptionResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionResponse> uniqueConstraintException(DataIntegrityViolationException ex) {
		log.error(ex.getMessage());

		ExceptionResponse res = new ExceptionResponse(failedStatus, "Add new category or subcategory name");

		return new ResponseEntity<ExceptionResponse>(res, HttpStatus.CONFLICT);

	}

}
