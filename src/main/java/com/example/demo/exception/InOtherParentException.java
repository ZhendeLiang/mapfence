package com.example.demo.exception;

public class InOtherParentException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InOtherParentException(String msg) {
		super(msg);
	}
}
