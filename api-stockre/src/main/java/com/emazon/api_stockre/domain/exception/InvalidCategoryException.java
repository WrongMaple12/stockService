package com.emazon.api_stockre.domain.exception;

public class InvalidCategoryException extends RuntimeException {
	public InvalidCategoryException(String message) {
		super(message);
	}
}
