package org.raiden.exception;

public class NotYetImplementedException extends RuntimeException {
	
	private static final long serialVersionUID = 7845965338955346095L;
	
	private String message;

	public NotYetImplementedException() {
		
	}
	
	public NotYetImplementedException(String message) {
		this.message = message;
	}

	public String toString() {
		return message;
	}

}
