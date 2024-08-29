package com.emazon.api_stockre.aplication.service.impl;
import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.domain.model.Pagina;
import com.emazon.api_stockre.domain.ports.input.ListarCategoriasUseCase;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataCategoriaRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class ListarCategoriasServiceImpl implements ListarCategoriasUseCase {
	private final DataCategoriaRepositorio categoriaRepositorio;


	public ListarCategoriasServiceImpl(DataCategoriaRepositorio categoriaRepositorio) {
		this.categoriaRepositorio = categoriaRepositorio;
	}

	@Override
	public Pagina<Categoria> listarCategorias(int pagina, int tamano, String direccionOrden, String campoOrden) {
		Sort.Direction sortDirection = "desc".equalsIgnoreCase(direccionOrden) ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(pagina, tamano, Sort.by(sortDirection, campoOrden));

		Page<CategoriaEntity> resultPage = categoriaRepositorio.findAll(pageable);

		List<Categoria> categorias = resultPage.getContent().stream()
			.map(this::convertToDomain)
			.toList();

		return new Pagina<>(
			categorias,
			resultPage.getNumber(),
			resultPage.getSize(),
			resultPage.getTotalPages(),
			resultPage.getTotalElements()
		);
	}

	private Categoria convertToDomain(CategoriaEntity entity) {
		return new Categoria(entity.getId(),entity.getNombre(), entity.getDescripcion());
	}

}

