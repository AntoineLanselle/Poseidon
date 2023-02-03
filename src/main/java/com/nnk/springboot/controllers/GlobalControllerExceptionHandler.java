package com.nnk.springboot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nnk.springboot.exceptions.RessourceNotFoundException;

/**
 * Controller for all exceptions.
 * 
 * @author Antoine Lanselle
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

	/**
	 * Handles the ResourceNotFound error.
	 * 
	 * @param RessourceNotFoundException the error.
	 * @return ResponseEntity<String> with status NOT_FOUND and error message as
	 *         body.
	 */
	@ExceptionHandler(RessourceNotFoundException.class)
	public ResponseEntity<String> handletNotFoundException(RessourceNotFoundException ex) {
		String error = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
