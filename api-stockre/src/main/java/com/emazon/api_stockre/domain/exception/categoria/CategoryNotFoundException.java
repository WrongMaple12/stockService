package com.emazon.api_stockre.domain.exception.categoria;

public class CategoryNotFoundException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "Error al crear la categoría: El nombre ya existe.";

	public CategoryNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public CategoryNotFoundException(String message) {
		super(message);
	}
}

