package com.emazon.api_stockre.infrastructure.controllers;

import com.emazon.api_stockre.aplication.dto.CategoriaDTO;
import com.emazon.api_stockre.domain.ports.input.CrearCategoriaServicio;
import com.emazon.api_stockre.aplication.service.impl.ListarCategoriasServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class CategoriaControladorTest {

	@Mock
	private CrearCategoriaServicio crearCategoriaServicio;

	@Mock
	private ListarCategoriasServiceImpl listarCategoriasService;

	@InjectMocks
	private CategoriaControlador categoriaControlador;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(categoriaControlador).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testCrearCategoriaExito() throws Exception {
		// Dado
		CategoriaDTO categoriaDTO = new CategoriaDTO(1L, "Categoria1", "Descripcion de Categoria");

		// Cuando
		mockMvc.perform(post("/api/categorias")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(categoriaDTO)))
			.andExpect(status().isCreated())
			.andExpect(content().string("Categoría creada con éxito"));
	}

	@Test
	void testCrearCategoriaDescripcionNula() throws Exception {
		// Dado
		CategoriaDTO categoriaDTO = new CategoriaDTO(1L, "Categoria1", null);

		// Cuando
		mockMvc.perform(post("/api/categorias")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(categoriaDTO)))
			.andExpect(status().isBadRequest())
			.andExpect(content().string("La descripción no puede ser nula o vacía."));
	}

	@Test
	void testListarCategoriasConDatos() throws Exception {
		// Dado
		CategoriaDTO categoriaDTO1 = new CategoriaDTO(1L, "Categoria1", "Descripcion 1");
		CategoriaDTO categoriaDTO2 = new CategoriaDTO(2L, "Categoria2", "Descripcion 2");
		List<CategoriaDTO> categorias = List.of(categoriaDTO1, categoriaDTO2);

		when(listarCategoriasService.listarCategorias(0, 10, "nombre", "asc")).thenReturn(categorias);

		// Cuando
		mockMvc.perform(get("/api/categorias")
				.param("pagina", "0")
				.param("tamano", "10")
				.param("ordenarPor", "nombre")
				.param("direccion", "asc"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].nombre", is("Categoria1")))
			.andExpect(jsonPath("$[1].nombre", is("Categoria2")));
	}

	@Test
	void testListarCategoriasSinDatos() throws Exception {
		// Dado
		when(listarCategoriasService.listarCategorias(0, 10, "nombre", "asc")).thenReturn(List.of());

		// Cuando
		mockMvc.perform(get("/api/categorias")
				.param("pagina", "0")
				.param("tamano", "10")
				.param("ordenarPor", "nombre")
				.param("direccion", "asc"))
			.andExpect(status().isNoContent());
	}
}

