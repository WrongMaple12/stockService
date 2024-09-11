package com.emazon.api_stockre.infrastructure.repositories;

import com.emazon.api_stockre.infrastructure.entities.MarcaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DataMarcaRepositorioTest {

	@Autowired
	private DataMarcaRepositorio marcaRepositorio;

	@BeforeEach
	void setUp() {
		marcaRepositorio.deleteAll(); // Limpiar todas las entradas en la tabla antes de cada prueba
	}

	@Test
	void testSaveMarcaConNombreValido() {
		// Crear una instancia de MarcaEntity con un nombre válido
		MarcaEntity marca = new MarcaEntity();
		marca.setNombre("Tecnología");
		marca.setDescripcion("Marca de dispositivos tecnológicos");

		// Guardar la marca y verificar que se haya guardado correctamente
		MarcaEntity savedMarca = marcaRepositorio.save(marca);
		assertNotNull(savedMarca.getId());
	}

	@Test
	@Transactional
	void testSaveMarcaConNombreDuplicadoDebeFallar() {
		// Crear la primera instancia de MarcaEntity con un nombre
		MarcaEntity marca1 = new MarcaEntity();
		marca1.setNombre("Tecnología");
		marca1.setDescripcion("Marca de dispositivos tecnológicos");

		// Guardar la primera marca
		marcaRepositorio.save(marca1);

		// Crear una segunda instancia de MarcaEntity con el mismo nombre
		MarcaEntity marca2 = new MarcaEntity();
		marca2.setNombre("Tecnología");
		marca2.setDescripcion("Productos de última generación");

		// Intentar guardar la segunda marca y esperar que falle debido a la restricción UNIQUE
		assertThrows(DataIntegrityViolationException.class, () -> {
			marcaRepositorio.save(marca2);
		});
	}

	@Test
	@Transactional
	void testSaveMarcaConNombreNuloDebeFallar() {
		// Crear una instancia de MarcaEntity sin un nombre
		MarcaEntity marca = new MarcaEntity();
		marca.setNombre(null);
		marca.setDescripcion("Descripción sin nombre");

		// Intentar guardar la marca y esperar que falle debido a la restricción de la base de datos
		assertThrows(DataIntegrityViolationException.class, () -> {
			marcaRepositorio.save(marca);
		});
	}
}

