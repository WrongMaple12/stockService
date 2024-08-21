package com.emazon.api_stockre.aplication.service.impl;

import com.emazon.api_stockre.aplication.dto.CategoriaDTO;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataCategoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

 class ListarCategoriasServiceImplTest {

	@Mock
	private DataCategoriaRepositorio categoriaRepositorio;

	@InjectMocks
	private ListarCategoriasServiceImpl listarCategoriasService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	private CategoriaEntity createCategoriaEntity(long id, String nombre, String descripcion) {
		CategoriaEntity categoria = new CategoriaEntity();
		categoria.setId(id);
		categoria.setNombre(nombre);
		categoria.setDescripcion(descripcion);
		return categoria;
	}

	@Test
	void testListarCategoriasOrdenarPorNombreAscendente() {
		// Dado
		CategoriaEntity categoria1 = createCategoriaEntity(1L, "B", "Descripcion B");
		CategoriaEntity categoria2 = createCategoriaEntity(2L, "A", "Descripcion A");
		CategoriaEntity categoria3 = createCategoriaEntity(3L, "C", "Descripcion C");

		when(categoriaRepositorio.findAll()).thenReturn(List.of(categoria3, categoria1, categoria2));

		// Cuando
		List<CategoriaDTO> resultado = listarCategoriasService.listarCategorias(0, 2, "nombre", "asc");

		// Entonces
		assertEquals(2, resultado.size());
		assertEquals("A", resultado.get(0).getNombre());
		assertEquals("B", resultado.get(1).getNombre());
	}

	@Test
	void testListarCategoriasOrdenarPorDescripcionDescendente() {
		// Dado
		CategoriaEntity categoria1 = createCategoriaEntity(1L, "A", "Descripcion C");
		CategoriaEntity categoria2 = createCategoriaEntity(2L, "B", "Descripcion A");
		CategoriaEntity categoria3 = createCategoriaEntity(3L, "C", "Descripcion B");

		when(categoriaRepositorio.findAll()).thenReturn(List.of(categoria1, categoria2, categoria3));

		// Cuando
		List<CategoriaDTO> resultado = listarCategoriasService.listarCategorias(0, 2, "descripcion", "desc");

		// Entonces
		assertEquals(2, resultado.size());
		assertEquals("Descripcion C", resultado.get(0).getDescripcion());
		assertEquals("Descripcion B", resultado.get(1).getDescripcion());
	}

	@Test
	void testListarCategoriasPaginacion() {
		// Dado
		CategoriaEntity categoria1 = createCategoriaEntity(1L, "A", "Descripcion A");
		CategoriaEntity categoria2 = createCategoriaEntity(2L, "B", "Descripcion B");
		CategoriaEntity categoria3 = createCategoriaEntity(3L, "C", "Descripcion C");

		when(categoriaRepositorio.findAll()).thenReturn(List.of(categoria1, categoria2, categoria3));

		// Cuando
		List<CategoriaDTO> resultado = listarCategoriasService.listarCategorias(1, 2, "nombre", "asc");

		// Entonces
		assertEquals(1, resultado.size());
		assertEquals("C", resultado.get(0).getNombre());
	}

	@Test
	void testListarCategoriasOrdenarPorNombreAscendenteCasoDeMayusculasYMinusculas() {
		// Dado
		CategoriaEntity categoria1 = createCategoriaEntity(1L, "b", "Descripcion B");
		CategoriaEntity categoria2 = createCategoriaEntity(2L, "a", "Descripcion A");
		CategoriaEntity categoria3 = createCategoriaEntity(3L, "C", "Descripcion C");

		when(categoriaRepositorio.findAll()).thenReturn(List.of(categoria1, categoria2, categoria3));

		// Cuando
		List<CategoriaDTO> resultado = listarCategoriasService.listarCategorias(0, 3, "nombre", "asc");

		// Entonces
		assertEquals(3, resultado.size());
		assertEquals("a", resultado.get(0).getNombre());
		assertEquals("b", resultado.get(1).getNombre());
		assertEquals("C", resultado.get(2).getNombre());
	}
}

