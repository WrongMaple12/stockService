package com.emazon.api_stockre.domain.ports.out;


import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;


import java.util.List;


public interface CategoriaRepositoryPort {
	boolean existsByNombre(String nombre);
	List<CategoriaEntity> findAll();
	}
