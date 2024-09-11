package com.emazon.api_stockre.aplication.service.impl;

import com.emazon.api_stockre.domain.model.Articulo;
import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.infrastructure.entities.ArticuloEntity;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataArticuloRepositorio;
import com.emazon.api_stockre.infrastructure.repositories.DataCategoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CrearArticuloServicioImplTest {

	@Mock
	private DataArticuloRepositorio dataArticuloRepositorio;

	@Mock
	private DataCategoriaRepositorio dataCategoriaRepositorio;

	@InjectMocks
	private CrearArticuloServicioImpl crearArticuloServicio;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCrearArticuloConCategoriasValidas() {
		// Configurar categorías (entidades de la base de datos)
		CategoriaEntity categoriaEntity1 = new CategoriaEntity();
		categoriaEntity1.setId(1L);
		CategoriaEntity categoriaEntity2 = new CategoriaEntity();
		categoriaEntity2.setId(2L);

		// Simular comportamiento del repositorio de categorías
		when(dataCategoriaRepositorio.findById(1L)).thenReturn(Optional.of(categoriaEntity1));
		when(dataCategoriaRepositorio.findById(2L)).thenReturn(Optional.of(categoriaEntity2));

		// Mapear CategoriaEntity a Categoria (si tienes un mapper)
		Categoria categoria1 = new Categoria();
		categoria1.setId(1L);
		Categoria categoria2 = new Categoria();
		categoria2.setId(2L);

		// Configurar artículo
		Articulo articulo = new Articulo();
		articulo.setNombre("Artículo de prueba");
		articulo.setDescripcion("Descripción de prueba");
		articulo.setCantidad(10);
		articulo.setPrecio(new BigDecimal("100.0"));
		articulo.setCategorias(List.of(categoria1, categoria2)); // Lista de Categoria, no CategoriaEntity

		// Llamar al método
		crearArticuloServicio.crearArticulo(articulo);

		// Verificar que se guardó el artículo
		verify(dataArticuloRepositorio, times(1)).save(any(ArticuloEntity.class));
	}

}
