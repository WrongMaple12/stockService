package com.emazon.api_stockre.infrastructure.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import com.emazon.api_stockre.infrastructure.entities.ArticuloEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DataArticuloRepositorioTest {
	@Autowired
	private DataArticuloRepositorio articuloRepositorio;
	@Test
	void testSaveArticulo() {
		// Crear una instancia de ArticuloEntity
		ArticuloEntity articulo = new ArticuloEntity();
		articulo.setNombre("Laptop");
		articulo.setDescripcion("Laptop de alto rendimiento");
		articulo.setCantidad(10);
		articulo.setPrecio(new BigDecimal("1500.00")); // Convertir double a BigDecimal

		// Guardar el artículo y verificar que se haya guardado correctamente
		ArticuloEntity savedArticulo = articuloRepositorio.save(articulo);
		assertNotNull(savedArticulo.getId());
	}

	@Test
	void testFindByIdArticulo() {
		// Crear y guardar un artículo
		ArticuloEntity articulo = new ArticuloEntity();
		articulo.setNombre("Smartphone");
		articulo.setDescripcion("Smartphone de última generación");
		articulo.setCantidad(20);
		articulo.setPrecio(new BigDecimal("800.00")); // Convertir double a BigDecimal
		ArticuloEntity savedArticulo = articuloRepositorio.save(articulo);

		// Recuperar el artículo por su ID
		Optional<ArticuloEntity> foundArticulo = articuloRepositorio.findById(savedArticulo.getId());
		assertTrue(foundArticulo.isPresent());
		assertEquals("Smartphone", foundArticulo.get().getNombre());
	}

	@Test
	void testDeleteArticulo() {
		// Crear y guardar un artículo
		ArticuloEntity articulo = new ArticuloEntity();
		articulo.setNombre("Tablet");
		articulo.setDescripcion("Tablet para profesionales");
		articulo.setCantidad(5);
		articulo.setPrecio(new BigDecimal("600.00")); // Convertir double a BigDecimal
		ArticuloEntity savedArticulo = articuloRepositorio.save(articulo);

		// Eliminar el artículo
		articuloRepositorio.deleteById(savedArticulo.getId());

		// Verificar que el artículo fue eliminado
		Optional<ArticuloEntity> deletedArticulo = articuloRepositorio.findById(savedArticulo.getId());
		assertFalse(deletedArticulo.isPresent());
	}

}
