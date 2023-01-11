package com.nnk.springboot.exceptions;

public class RessourceNotFoundException extends Exception {

	private static final long serialVersionUID = 2L;

	/**
	 * Creates an RessourceNotFoundException.
	 * 
	 * @param the message of the exception.
	 * 
	 */
	public RessourceNotFoundException(String message) {
		super(message);
	}

}
