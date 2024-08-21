package com.emazon.api_stockre.aplication.service.impl;

import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataCategoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CrearCategoriaServicioImplTest {
	@Mock
	private DataCategoriaRepositorio categoriaRepositorio;

	@InjectMocks
	private CrearCategoriaServicioImpl crearCategoriaServicio;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCrearCategoria() {

		Categoria categoria = new Categoria();
		categoria.setNombre("Electronics");
		categoria.setDescripcion("Electronics category");


		crearCategoriaServicio.crearCategoria(categoria);


		verify(categoriaRepositorio, times(1)).save(any(CategoriaEntity.class));
	}

	@Test
	void testCategoriaExistePorNombre() {
		// Dado
		String nombre = "Electronics";
		when(categoriaRepositorio.existsByNombre(nombre)).thenReturn(true);

		// Cuando
		boolean existe = crearCategoriaServicio.categoriaExistePorNombre(nombre);

		// Entonces
		assertTrue(existe);
		verify(categoriaRepositorio, times(1)).existsByNombre(nombre);
	}
}
