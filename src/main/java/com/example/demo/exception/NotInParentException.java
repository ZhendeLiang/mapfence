package com.example.demo.exception;

public class NotInParentException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NotInParentException(String msg) {
		super(msg);
	}
}
