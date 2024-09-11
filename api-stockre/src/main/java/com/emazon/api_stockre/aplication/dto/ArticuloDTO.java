package com.emazon.api_stockre.aplication.dto;

import java.math.BigDecimal;
import java.util.List;

public class ArticuloDTO {
	private Long id;
	private String nombre;
	private String descripcion;
	private int cantidad;
	private BigDecimal precio;
	private List<Long> categoriasIds;
	public ArticuloDTO() {

	}
	public ArticuloDTO(Long id, String nombre, String descripcion, int cantidad, BigDecimal precio, List<Long> categoriasIds) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.precio = precio;
		this.categoriasIds = categoriasIds;
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
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public List<Long> getCategoriasIds() {
		return categoriasIds;
	}

	public void setCategoriasIds(List<Long> categoriasIds) {
		this.categoriasIds = categoriasIds;
	}
}
