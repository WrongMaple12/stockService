package com.emazon.api_stockre.infrastructure.adapters;
import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.domain.model.Pagina;
import com.emazon.api_stockre.domain.ports.out.CategoriaRepositoryPort;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataCategoriaRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


@Component
public class CategoriaRepositorioAdapter implements CategoriaRepositoryPort {


	private DataCategoriaRepositorio dataCategoriaRepositorio;


	@Override
	public boolean existsByNombre(String nombre) {
		return dataCategoriaRepositorio.existsByNombre(nombre);
	}

	@Override
	public Pagina<Categoria> findAll(int pagina, int tamano, String direccionOrden, String campoOrden) {
		// Convertir a Sort.Direction (ASC o DESC)
		Sort.Direction sortDirection = direccionOrden.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

		// Crear un objeto PageRequest
		PageRequest pageRequest = PageRequest.of(pagina, tamano, Sort.by(sortDirection, campoOrden));

		// Llamar al metodo que devuelve una pagina de entidades
		Page<CategoriaEntity> categoriaEntitiesPage = dataCategoriaRepositorio.findAll(pageRequest);

		// Convertir la página de entidades a una página de dominio
		return new Pagina<>(
			categoriaEntitiesPage.getContent().stream()
				.map(entity -> new Categoria(entity.getId(), entity.getNombre(), entity.getDescripcion()))
				.toList(),
			categoriaEntitiesPage.getNumber(),
			categoriaEntitiesPage.getSize(),
			categoriaEntitiesPage.getTotalPages(),
			categoriaEntitiesPage.getTotalElements()
		);
	}

}

