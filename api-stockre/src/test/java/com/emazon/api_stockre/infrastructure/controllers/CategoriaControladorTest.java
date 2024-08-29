package com.emazon.api_stockre.infrastructure.controllers;

import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.domain.model.Pagina;
import com.emazon.api_stockre.domain.ports.input.CrearCategoriaServicio;
import com.emazon.api_stockre.domain.ports.input.ListarCategoriasUseCase;
import com.emazon.api_stockre.aplication.dto.CategoriaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoriaControladorTest {

	@InjectMocks
	private CategoriaControlador categoriaControlador;

	@Mock
	private CrearCategoriaServicio crearCategoriaServicio;

	@Mock
	private ListarCategoriasUseCase listarCategoriasUseCase;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void crearCategoria_Success() {
		CategoriaDTO categoriaDTO = new CategoriaDTO("nombreCategoria", "descripcionCategoria");
		ResponseEntity<String> response = categoriaControlador.crearCategoria(categoriaDTO);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Categoría creada con éxito", response.getBody());
	}

	@Test
	void crearCategoria_NombreYDescripcionRequeridos() {
		CategoriaDTO categoriaDTO = new CategoriaDTO(null, null);
		ResponseEntity<String> response = categoriaControlador.crearCategoria(categoriaDTO);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Nombre y descripción son obligatorios.", response.getBody());
	}

	@Test
	void crearCategoria_NombreLargo() {
		CategoriaDTO categoriaDTO = new CategoriaDTO("nombreCategoriaMuyLargoQueExcedeElLimiteDeCincuentaCaracteres", "descripcionCategoria");
		ResponseEntity<String> response = categoriaControlador.crearCategoria(categoriaDTO);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("El nombre de la categoría no puede superar los 50 caracteres.", response.getBody());
	}
	@Test
	void CrearCategoria_DescripcionLarga() {
		CategoriaDTO categoriaDTO = new CategoriaDTO("Electronics", "A".repeat(91));

		ResponseEntity<String> response = categoriaControlador.crearCategoria(categoriaDTO);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("La descripción de la categoría no puede superar los 90 caracteres.", response.getBody());
	}





	@Test
	void crearCategoria_ErrorInterno() {
		CategoriaDTO categoriaDTO = new CategoriaDTO("nombreCategoria", "descripcionCategoria");

		doThrow(new RuntimeException("Error interno")).when(crearCategoriaServicio).crearCategoria(any(Categoria.class));

		ResponseEntity<String> response = categoriaControlador.crearCategoria(categoriaDTO);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Error interno del servidor.", response.getBody());
	}

	@Test
	void listarCategorias_Success() {
		Pagina<Categoria> paginaEsperada = new Pagina<>(
			Collections.singletonList(new Categoria("nombreCategoria", "descripcionCategoria")),
			0, 10, 1, 1L
		);

		when(listarCategoriasUseCase.listarCategorias(0, 10, "asc", "nombre")).thenReturn(paginaEsperada);

		ResponseEntity<Pagina<Categoria>> response = categoriaControlador.listarCategorias(0, 10, "asc", "nombre");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(paginaEsperada, response.getBody());
	}
}
