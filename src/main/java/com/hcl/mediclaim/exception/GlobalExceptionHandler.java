package com.hcl.mediclaim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hcl.mediclaim.utility.MediClaimUtility;

/**
 * @author Laxman
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<ErrorResponse> invalidUserExceptionHandler(InvalidUserException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), MediClaimUtility.ERROR_RESPONSE_FAIL);

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidPolicyIdException.class)
	public ResponseEntity<ErrorResponse> invalidPolicyIdExceptionHandler(InvalidPolicyIdException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), MediClaimUtility.ERROR_RESPONSE_FAIL);

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RemarksEmptyException.class)
	public ResponseEntity<ErrorResponse> remarksEmptyExceptionHandler(RemarksEmptyException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), MediClaimUtility.ERROR_RESPONSE_FAIL);

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

}
