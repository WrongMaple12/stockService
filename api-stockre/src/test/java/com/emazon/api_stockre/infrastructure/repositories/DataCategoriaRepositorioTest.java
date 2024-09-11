package com.emazon.api_stockre.infrastructure.repositories;


import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig

@SpringBootTest
class DataCategoriaRepositorioTest {
	@Autowired
	private DataCategoriaRepositorio categoriaRepositorio;
	@BeforeEach
	void setUp() {
		categoriaRepositorio.deleteAll(); // Limpiar todas las entradas en la tabla antes de cada prueba
	}

	@Test
	void testSaveCategoriaConDescripcionValida() {
		// Crear una instancia de CategoriaEntity con una descripción válida
		CategoriaEntity categoria = new CategoriaEntity();
		categoria.setNombre("Electrónica");
		categoria.setDescripcion("Dispositivos electrónicos");

		// Guardar la categoría y verificar que se haya guardado correctamente
		CategoriaEntity savedCategoria = categoriaRepositorio.save(categoria);
		assertNotNull(savedCategoria.getId());
	}

	@Test
	void testSaveCategoriaConNombreDuplicadoDebeFallar() {
		// Crear la primera instancia de CategoriaEntity con un nombre
		CategoriaEntity categoria1 = new CategoriaEntity();
		categoria1.setNombre("Electrónica");
		categoria1.setDescripcion("Dispositivos electrónicos");

		// Guardar la primera categoría
		categoriaRepositorio.save(categoria1);

		// Crear una segunda instancia de CategoriaEntity con el mismo nombre
		CategoriaEntity categoria2 = new CategoriaEntity();
		categoria2.setNombre("Electrónica");
		categoria2.setDescripcion("Gadgets y más");

		// Intentar guardar la segunda categoría y esperar que falle debido a la restricción UNIQUE
		assertThrows(DataIntegrityViolationException.class, () -> {
			categoriaRepositorio.save(categoria2);
		});
	}

	@Test
	void testSaveCategoriaConDescripcionNulaDebeFallar() {
		// Crear una instancia de CategoriaEntity sin una descripción
		CategoriaEntity categoria = new CategoriaEntity();
		categoria.setNombre("Electrónica");
		categoria.setDescripcion(null);

		// Intentar guardar la categoría y esperar que falle debido a la restricción de la base de datos
		assertThrows(DataIntegrityViolationException.class, () -> {
			categoriaRepositorio.save(categoria);
		});
	}
}
