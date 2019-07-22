package com.aceproject.demo.exception;

public class NotEnoughRandomPlayerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5471092271033897205L;

	public NotEnoughRandomPlayerException(String msg) {
		super(msg);
	}
}
