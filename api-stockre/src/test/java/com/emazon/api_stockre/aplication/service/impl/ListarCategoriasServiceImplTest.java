package com.emazon.api_stockre.aplication.service.impl;
import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.domain.model.Pagina;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataCategoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import java.util.Collections;

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

	@Test
	void testListarCategoriasConDatos() {
		CategoriaEntity categoriaEntity = new CategoriaEntity();
		categoriaEntity.setId(1L);
		categoriaEntity.setNombre("Electronics");
		categoriaEntity.setDescripcion("Electronics and gadgets");

		Page<CategoriaEntity> pagedResponse = new PageImpl<>(
			Collections.singletonList(categoriaEntity),
			PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre")),
			1
		);

		when(categoriaRepositorio.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre"))))
			.thenReturn(pagedResponse);

		Pagina<Categoria> resultado = listarCategoriasService.listarCategorias(0, 10, "asc", "nombre");

		assertEquals(1, resultado.getContent().size());
		assertEquals("Electronics", resultado.getContent().get(0).getNombre());
		assertEquals("Electronics and gadgets", resultado.getContent().get(0).getDescripcion());
		assertEquals(0, resultado.getCurrentPage());
		assertEquals(10, resultado.getPageSize());
		assertEquals(1, resultado.getTotalPages());
		assertEquals(1, resultado.getTotalElements());
	}

	@Test
	void testListarCategoriasSinDatos() {
		Page<CategoriaEntity> pagedResponse = new PageImpl<>(
			Collections.emptyList(),
			PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre")),
			0
		);

		when(categoriaRepositorio.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nombre"))))
			.thenReturn(pagedResponse);

		Pagina<Categoria> resultado = listarCategoriasService.listarCategorias(0, 10, "asc", "nombre");

		assertEquals(0, resultado.getContent().size());
		assertEquals(0, resultado.getCurrentPage());
		assertEquals(10, resultado.getPageSize());
		assertEquals(0, resultado.getTotalPages());
		assertEquals(0, resultado.getTotalElements());
	}
}
