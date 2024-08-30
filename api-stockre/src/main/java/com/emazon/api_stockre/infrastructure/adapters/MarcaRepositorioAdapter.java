package com.emazon.api_stockre.infrastructure.adapters;

import com.emazon.api_stockre.domain.model.Marca;
import com.emazon.api_stockre.domain.model.PaginaMarca;
import com.emazon.api_stockre.domain.ports.out.MarcaRepositoryPort;
import com.emazon.api_stockre.infrastructure.entities.MarcaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataMarcaRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class MarcaRepositorioAdapter implements MarcaRepositoryPort {
	private DataMarcaRepositorio dataMarcaRepositorio;
	@Override
	public boolean existsByNombre(String nombre) {
		return dataMarcaRepositorio.existsByNombre(nombre);
	}

	@Override
	public PaginaMarca<Marca> findAll(int pagina, int tamano, String direccionOrden, String campoOrden) {
		// Convertir a Sort.Direction (ASC o DESC)
		Sort.Direction sortDirection = direccionOrden.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

		// Crear un objeto PageRequest
		PageRequest pageRequest = PageRequest.of(pagina, tamano, Sort.by(sortDirection, campoOrden));

		// Llamar al metodo que devuelve una pagina de entidades
		Page<MarcaEntity> marcaEntitiesPage = dataMarcaRepositorio.findAll(pageRequest);

		// Convertir la página de entidades a una página de dominio
		return new PaginaMarca<>(
			marcaEntitiesPage.getContent().stream()
				.map(entity -> new Marca(entity.getId(), entity.getNombre(), entity.getDescripcion()))
				.toList(),
			marcaEntitiesPage.getNumber(),
			marcaEntitiesPage.getSize(),
			marcaEntitiesPage.getTotalPages(),
			marcaEntitiesPage.getTotalElements()
		);

	}
}
