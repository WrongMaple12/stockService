package com.emazon.api_stockre.infrastructure.adapters;
import com.emazon.api_stockre.domain.ports.out.CategoriaRepositoryPort;


import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataCategoriaRepositorio;



import java.util.List;


public class CategoriaRepositorioAdapter implements CategoriaRepositoryPort {


	private DataCategoriaRepositorio dataCategoriaRepositorio;


	@Override
	public boolean existsByNombre(String nombre) {
		return dataCategoriaRepositorio.existsByNombre(nombre);
	}


	@Override
	public List<CategoriaEntity> findAll() {
		return dataCategoriaRepositorio.findAll();
	}

}
