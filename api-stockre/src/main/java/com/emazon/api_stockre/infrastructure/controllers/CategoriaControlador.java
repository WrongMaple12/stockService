package com.emazon.api_stockre.infrastructure.controllers;

import com.emazon.api_stockre.aplication.dto.CategoriaDTO;
import com.emazon.api_stockre.domain.ports.input.CrearCategoriaServicio;
import com.emazon.api_stockre.aplication.service.impl.ListarCategoriasServiceImpl;
import com.emazon.api_stockre.domain.model.Categoria;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaControlador {

	private final CrearCategoriaServicio crearCategoriaServicio;
	private final ListarCategoriasServiceImpl listarCategoriasService;

	public CategoriaControlador(CrearCategoriaServicio crearCategoriaServicio, ListarCategoriasServiceImpl listarCategoriasService) {
		this.crearCategoriaServicio = crearCategoriaServicio;
		this.listarCategoriasService = listarCategoriasService;
	}

	@PostMapping
	public ResponseEntity<String> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {

		if (categoriaDTO.getDescripcion() == null || categoriaDTO.getDescripcion().trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La descripción no puede ser nula o vacía.");
		}

		try {
			Categoria categoria = new Categoria(categoriaDTO.getNombre(), categoriaDTO.getDescripcion());

			crearCategoriaServicio.crearCategoria(categoria);

			return ResponseEntity.status(HttpStatus.CREATED).body("Categoría creada con éxito");

		} catch (DataIntegrityViolationException e) {

			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la categoría: El nombre ya existe.");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la categoría: El nombre es demasiado largo.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la categoría: " + e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> listarCategorias(
		@RequestParam(defaultValue = "0") int pagina,
		@RequestParam(defaultValue = "5") int tamano,
		@RequestParam(defaultValue = "nombre") String ordenarPor,
		@RequestParam(defaultValue = "asc") String direccion) {


		List<CategoriaDTO> categorias = listarCategoriasService.listarCategorias(pagina, tamano, ordenarPor, direccion);


		if (categorias.isEmpty()) {
			return ResponseEntity.noContent().build(); // 204 No Content si no hay categorías
		}

		return ResponseEntity.ok(categorias); // 200 OK con la lista de categorías
	}
}





