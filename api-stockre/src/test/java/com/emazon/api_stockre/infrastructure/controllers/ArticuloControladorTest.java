package com.emazon.api_stockre.infrastructure.controllers;
import com.emazon.api_stockre.aplication.dto.ArticuloDTO;
import com.emazon.api_stockre.domain.model.Articulo;
import com.emazon.api_stockre.domain.ports.input.CrearArticuloServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArticuloControladorTest {

	@InjectMocks
	private ArticuloControlador articuloControlador;

	@Mock
	private CrearArticuloServicio crearArticuloServicio;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void crearArticulo_Success() {
		ArticuloDTO articuloDTO = new ArticuloDTO();
		articuloDTO.setId(1L);
		articuloDTO.setNombre("Artículo 1");
		articuloDTO.setDescripcion("Descripción del artículo 1");
		articuloDTO.setCantidad(10);
		articuloDTO.setPrecio(new BigDecimal("100.00"));
		articuloDTO.setCategoriasIds(Arrays.asList(1L, 2L));

		ResponseEntity<String> response = articuloControlador.crearArticulo(articuloDTO);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Artículo creado con éxito", response.getBody());
	}

	@Test
	void crearArticulo_NumeroCategoriasInvalido() {
		ArticuloDTO articuloDTO = new ArticuloDTO();
		articuloDTO.setCategoriasIds(Collections.emptyList());

		ResponseEntity<String> response = articuloControlador.crearArticulo(articuloDTO);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("El artículo debe tener entre 1 y 3 categorías asociadas.", response.getBody());
	}

	@Test
	void crearArticulo_CategoriasDuplicadas() {
		ArticuloDTO articuloDTO = new ArticuloDTO();
		articuloDTO.setCategoriasIds(Arrays.asList(1L, 1L, 2L));

		ResponseEntity<String> response = articuloControlador.crearArticulo(articuloDTO);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Las IDs de las categorías no pueden estar repetidas.", response.getBody());
	}

	@Test
	void crearArticulo_ErrorInterno() {
		ArticuloDTO articuloDTO = new ArticuloDTO();
		articuloDTO.setId(1L);
		articuloDTO.setNombre("Artículo 1");
		articuloDTO.setDescripcion("Descripción del artículo 1");
		articuloDTO.setCantidad(10);
		articuloDTO.setPrecio(new BigDecimal("100.00"));
		articuloDTO.setCategoriasIds(Arrays.asList(1L, 2L));

		doThrow(new RuntimeException("Error interno")).when(crearArticuloServicio).crearArticulo(any(Articulo.class));

		ResponseEntity<String> response = articuloControlador.crearArticulo(articuloDTO);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Error interno del servidor.", response.getBody());
	}
}
