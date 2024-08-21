package com.emazon.api_stockre.aplication.service.impl;
import com.emazon.api_stockre.aplication.dto.CategoriaDTO;
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
	public List<CategoriaDTO> listarCategorias(int pagina, int tamano, String ordenarPor, String direccion) {

		Sort sort = Sort.by(Sort.Direction.fromString(direccion), ordenarPor);


		Pageable pageable = PageRequest.of(pagina, tamano, sort);


		Page<CategoriaEntity> categoriasPage = categoriaRepositorio.findAll(pageable);


		return categoriasPage.stream()
			.map(categoria -> new CategoriaDTO(categoria.getId(), categoria.getNombre(), categoria.getDescripcion()))
			.toList();
	}

}

