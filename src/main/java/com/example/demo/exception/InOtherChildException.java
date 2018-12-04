package com.example.demo.exception;

public class InOtherChildException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InOtherChildException(String msg) {
		super(msg);
	}
}
