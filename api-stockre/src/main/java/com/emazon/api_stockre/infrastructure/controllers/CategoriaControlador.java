package com.emazon.api_stockre.infrastructure.controllers;

import com.emazon.api_stockre.aplication.dto.CategoriaDTO;
import com.emazon.api_stockre.domain.model.Pagina;
import com.emazon.api_stockre.domain.ports.input.CrearCategoriaServicio;
import com.emazon.api_stockre.domain.model.Categoria;

import com.emazon.api_stockre.domain.ports.input.ListarCategoriasUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	@Operation(summary = "Crear una nueva categoría",
		description = "Este endpoint permite crear una nueva categoría. Se validan la longitud y existencia del nombre y la descripción.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Categoría creada con éxito"),
		@ApiResponse(responseCode = "400", description = "Error en la solicitud, nombre o descripción inválidos", content = @Content(schema = @Schema(type = "string"))),
		@ApiResponse(responseCode = "409", description = "Nombre de categoría ya existe", content = @Content(schema = @Schema(type = "string"))),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(type = "string")))
	})

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
	@Operation(summary = "Listar categorías paginadas",
		description = "Este endpoint permite listar categorías de manera paginada, con opciones de ordenación y filtrado.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de categorías devuelta con éxito"),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})

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
