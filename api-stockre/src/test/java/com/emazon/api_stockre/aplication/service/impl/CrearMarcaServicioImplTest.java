package com.emazon.api_stockre.aplication.service.impl;
import com.emazon.api_stockre.domain.model.Marca;
import com.emazon.api_stockre.infrastructure.entities.MarcaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataMarcaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CrearMarcaServicioImplTest {
	@Mock
	private DataMarcaRepositorio marcaRepositorio;

	@InjectMocks
	private CrearMarcaServicioImpl crearMarcaServicio;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void crearMarca_deberiaGuardarMarcaEnElRepositorio() {
		// Arrange
		Marca marca = new Marca("Nombre de Marca", "Descripción de Marca");

		// Act
		crearMarcaServicio.crearMarca(marca);

		// Assert
		ArgumentCaptor<MarcaEntity> marcaEntityCaptor = ArgumentCaptor.forClass(MarcaEntity.class);
		verify(marcaRepositorio).save(marcaEntityCaptor.capture());
		MarcaEntity marcaGuardada = marcaEntityCaptor.getValue();

		assertEquals("Nombre de Marca", marcaGuardada.getNombre());
		assertEquals("Descripción de Marca", marcaGuardada.getDescripcion());
	}

	@Test
	void categoriaExistePorNombre_deberiaRetornarTrueSiMarcaExiste() {
		// Arrange
		String nombre = "Nombre de Marca";
		when(marcaRepositorio.existsByNombre(nombre)).thenReturn(true);

		// Act
		boolean existe = crearMarcaServicio.categoriaExistePorNombre(nombre);

		// Assert
		assertTrue(existe);
		verify(marcaRepositorio).existsByNombre(nombre);
	}

	@Test
	void categoriaExistePorNombre_deberiaRetornarFalseSiMarcaNoExiste() {
		// Arrange
		String nombre = "Nombre de Marca";
		when(marcaRepositorio.existsByNombre(nombre)).thenReturn(false);

		// Act
		boolean existe = crearMarcaServicio.categoriaExistePorNombre(nombre);

		// Assert
		assertFalse(existe);
		verify(marcaRepositorio).existsByNombre(nombre);
	}
}
