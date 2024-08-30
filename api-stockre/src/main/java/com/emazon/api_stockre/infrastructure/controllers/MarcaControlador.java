package com.emazon.api_stockre.infrastructure.controllers;

import com.emazon.api_stockre.aplication.dto.MarcaDTO;
import com.emazon.api_stockre.domain.model.Marca;
import com.emazon.api_stockre.domain.model.PaginaMarca;
import com.emazon.api_stockre.domain.ports.input.CrearMarcaServicio;
import com.emazon.api_stockre.domain.ports.input.ListarMarcasUseCase;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marca")
public class MarcaControlador {
	private final CrearMarcaServicio crearMarcaServicio;
	private final ListarMarcasUseCase listarMarcasUseCase;

	public MarcaControlador(CrearMarcaServicio crearMarcaServicio, ListarMarcasUseCase listarMarcasUseCase) {
		this.crearMarcaServicio = crearMarcaServicio;
		this.listarMarcasUseCase = listarMarcasUseCase;
	}
	@PostMapping
	public ResponseEntity<String> crearMarca(@RequestBody MarcaDTO marcaDTO) {
		if (marcaDTO.getNombre() == null || marcaDTO.getNombre().trim().isEmpty() ||
			marcaDTO.getDescripcion() == null || marcaDTO.getDescripcion().trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Nombre y descripción son obligatorios.");
		}
		if (marcaDTO.getNombre().length() > 50) {
			return ResponseEntity.badRequest().body("El nombre de la marca no puede superar los 50 caracteres.");
		}
		if (marcaDTO.getDescripcion().length() > 120) {
			return ResponseEntity.badRequest().body("La descripción de la marca no puede superar los 120 caracteres.");
		}

		try {
			Marca marca = new Marca(marcaDTO.getNombre(), marcaDTO.getDescripcion());
			crearMarcaServicio.crearMarca(marca);
			return ResponseEntity.status(HttpStatus.CREATED).body("Marca creada con éxito");
		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Nombre de marca ya existe.");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la solicitud.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
		}
	}
	@GetMapping
	public ResponseEntity<PaginaMarca<Marca>> listarMarcas(
		@RequestParam(value = "pagina", defaultValue = "0") int pagina,
		@RequestParam(value = "tamaño", defaultValue = "10") int tamano,
		@RequestParam(value = "direccionOrden", defaultValue = "asc") String direccionOrden,
		@RequestParam(value = "campoOrden", defaultValue = "nombre") String campoOrden) {

		PaginaMarca<Marca> marcasPaginadas = listarMarcasUseCase.listarMarcas(pagina, tamano, direccionOrden, campoOrden);
		return ResponseEntity.ok(marcasPaginadas);
	}

}
