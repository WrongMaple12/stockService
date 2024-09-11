package com.emazon.api_stockre.infrastructure.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "articulostock")
public class ArticuloEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private String descripcion;
	private int cantidad;
	private BigDecimal precio;

	@ManyToMany
	@JoinTable(
		name = "articulo_categorias",
		joinColumns = @JoinColumn(name = "articulo_id"),
		inverseJoinColumns = @JoinColumn(name = "categoria_id")
	)
	private List<CategoriaEntity> categorias;
	public ArticuloEntity() {
	}
	public ArticuloEntity(Long id, String nombre, String descripcion, int cantidad, BigDecimal precio, List<CategoriaEntity> categorias) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.precio = precio;
		this.categorias = categorias;
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

	public List<CategoriaEntity> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaEntity> categorias) {
		this.categorias = categorias;
	}
}
