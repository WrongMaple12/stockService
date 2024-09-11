package com.emazon.api_stockre.infrastructure.controllers;

import com.emazon.api_stockre.aplication.dto.ArticuloDTO;
import com.emazon.api_stockre.domain.model.Articulo;
import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.domain.ports.input.CrearArticuloServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articulo")
public class ArticuloControlador {
	private final CrearArticuloServicio crearArticuloServicio;

	public ArticuloControlador(CrearArticuloServicio crearArticuloServicio) {
		this.crearArticuloServicio = crearArticuloServicio;
	}

	@PostMapping
	public ResponseEntity<String> crearArticulo(@RequestBody ArticuloDTO articuloDTO) {

		if (articuloDTO.getCategoriasIds() == null || articuloDTO.getCategoriasIds().isEmpty() || articuloDTO.getCategoriasIds().size() > 3) {
			return ResponseEntity.badRequest().body("El artículo debe tener entre 1 y 3 categorías asociadas.");
		}

		// Validación de categorías duplicadas
		boolean tieneDuplicados = articuloDTO.getCategoriasIds().stream().distinct().count() != articuloDTO.getCategoriasIds().size();
		if (tieneDuplicados) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Las IDs de las categorías no pueden estar repetidas.");
		}


		List<Categoria> categorias = articuloDTO.getCategoriasIds().stream()
			.map(id -> new Categoria(id, null, null))
			.toList();


		Articulo articulo = new Articulo(
			articuloDTO.getId(),
			articuloDTO.getNombre(),
			articuloDTO.getDescripcion(),
			articuloDTO.getCantidad(),
			articuloDTO.getPrecio(),
			categorias
		);

		try {
			crearArticuloServicio.crearArticulo(articulo);
			return ResponseEntity.status(HttpStatus.CREATED).body("Artículo creado con éxito");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
		}
	}
}









