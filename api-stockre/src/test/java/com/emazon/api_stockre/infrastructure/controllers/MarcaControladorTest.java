package com.emazon.api_stockre.infrastructure.controllers;
import com.emazon.api_stockre.aplication.dto.MarcaDTO;
import com.emazon.api_stockre.domain.model.Marca;
import com.emazon.api_stockre.domain.ports.input.CrearMarcaServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
class MarcaControladorTest {
	private MockMvc mockMvc;

	@Mock
	private CrearMarcaServicio crearMarcaServicio;

	@InjectMocks
	private MarcaControlador marcaControlador;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(marcaControlador).build();
	}

	@Test
	void crearMarcaSiNombreODescripcionEsNulo() throws Exception {

		mockMvc.perform(post("/api/marca")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nombre\":null, \"descripcion\":\"Descripción de Marca\"}"))
			.andExpect(status().isBadRequest())
			.andExpect(content().string("Nombre y descripción son obligatorios."));
	}

	@Test
	void crearMarcaSiNombreSuperaLimite() throws Exception {
		String nombreLargo = "Este es un nombre de marca que supera los 50 caracteres";

		mockMvc.perform(post("/api/marca")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nombre\":\"" + nombreLargo + "\", \"descripcion\":\"Descripción de Marca\"}"))
			.andExpect(status().isBadRequest())
			.andExpect(content().string("El nombre de la marca no puede superar los 50 caracteres."));
	}

	@Test
	void crearMarcaSiDescripcionSuperaLimite()  {
		MarcaDTO marcaDTO = new MarcaDTO("Electronics", "A".repeat(121));

		ResponseEntity<String> response = marcaControlador.crearMarca(marcaDTO);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("La descripción de la marca no puede superar los 120 caracteres.", response.getBody());
	}

	@Test
	void crearMarcaCreadaExitosamente() throws Exception {

		mockMvc.perform(post("/api/marca")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nombre\":\"Nombre de Marca\", \"descripcion\":\"Descripción de Marca\"}"))
			.andExpect(status().isCreated())
			.andExpect(content().string("Marca creada con éxito"));

		verify(crearMarcaServicio).crearMarca(any(Marca.class));
	}

	@Test
	void crearMarcaConflictSiNombreDeMarcaYaExiste() throws Exception {
		doThrow(new DataIntegrityViolationException("Duplicate key", new ConstraintViolationException(null)))
			.when(crearMarcaServicio).crearMarca(any(Marca.class));

		mockMvc.perform(post("/api/marca")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nombre\":\"Nombre de Marca\", \"descripcion\":\"Descripción de Marca\"}"))
			.andExpect(status().isConflict())
			.andExpect(content().string("Nombre de marca ya existe."));
	}

	@Test
	void crearMarcaInternalServerErrorSiOcurreOtraExcepcion() throws Exception {
		doThrow(new RuntimeException("Unexpected error")).when(crearMarcaServicio).crearMarca(any(Marca.class));

		mockMvc.perform(post("/api/marca")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nombre\":\"Nombre de Marca\", \"descripcion\":\"Descripción de Marca\"}"))
			.andExpect(status().isInternalServerError())
			.andExpect(content().string("Error interno del servidor."));
	}
}
