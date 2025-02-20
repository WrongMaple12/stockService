package com.emazon.api_stockre.aplication.dto;

import jakarta.persistence.Column;



public class CategoriaDTO {
	private Long id;
	@Column(length = 50, nullable = false, unique = true)
	private String nombre;
	@Column(length = 90, nullable = false)
	private String descripcion;

	public CategoriaDTO(Long id, String nombre, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre= nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
