package com.nnk.springboot.exceptions;

public class AlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates an AlreadyExistException.
	 * 
	 * @param the message of the exception.
	 * 
	 */
	public AlreadyExistException(String message) {
		super(message);
	}
	
}
