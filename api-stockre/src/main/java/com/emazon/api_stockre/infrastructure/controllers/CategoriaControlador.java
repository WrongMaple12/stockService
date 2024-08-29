package com.emazon.api_stockre.infrastructure.controllers;

import com.emazon.api_stockre.aplication.dto.CategoriaDTO;
import com.emazon.api_stockre.domain.model.Pagina;
import com.emazon.api_stockre.domain.ports.input.CrearCategoriaServicio;
import com.emazon.api_stockre.domain.model.Categoria;

import com.emazon.api_stockre.domain.ports.input.ListarCategoriasUseCase;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/categorias")
public class CategoriaControlador {

	private final CrearCategoriaServicio crearCategoriaServicio;
	private final ListarCategoriasUseCase listarCategoriasUseCase;

	public CategoriaControlador(CrearCategoriaServicio crearCategoriaServicio, ListarCategoriasUseCase listarCategoriasUseCase) {
		this.crearCategoriaServicio = crearCategoriaServicio;
		this.listarCategoriasUseCase = listarCategoriasUseCase;

	}

	@PostMapping
	public ResponseEntity<String> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
		if (categoriaDTO.getNombre() == null || categoriaDTO.getNombre().trim().isEmpty() ||
			categoriaDTO.getDescripcion() == null || categoriaDTO.getDescripcion().trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Nombre y descripción son obligatorios.");
		}
		if (categoriaDTO.getNombre().length() > 50) {
			return ResponseEntity.badRequest().body("El nombre de la categoría no puede superar los 50 caracteres.");
		}
		if (categoriaDTO.getDescripcion().length() > 90) {
			return ResponseEntity.badRequest().body("La descripción de la categoría no puede superar los 90 caracteres.");
		}

		try {
			Categoria categoria = new Categoria(categoriaDTO.getNombre(), categoriaDTO.getDescripcion());
			crearCategoriaServicio.crearCategoria(categoria);
			return ResponseEntity.status(HttpStatus.CREATED).body("Categoría creada con éxito");
		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Nombre de categoría ya existe.");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la solicitud.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
		}
	}

	@GetMapping
	public ResponseEntity<Pagina<Categoria>> listarCategorias(
		@RequestParam(value = "pagina", defaultValue = "0") int pagina,
		@RequestParam(value = "tamaño", defaultValue = "10") int tamano,
		@RequestParam(value = "direccionOrden", defaultValue = "asc") String direccionOrden,
		@RequestParam(value = "campoOrden", defaultValue = "nombre") String campoOrden) {

		Pagina<Categoria> categoriasPaginadas = listarCategoriasUseCase.listarCategorias(pagina, tamano, direccionOrden, campoOrden);
		return ResponseEntity.ok(categoriasPaginadas);
	}
}
