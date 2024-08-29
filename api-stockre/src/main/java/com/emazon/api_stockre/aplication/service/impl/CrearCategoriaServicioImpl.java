package com.emazon.api_stockre.aplication.service.impl;

import com.emazon.api_stockre.domain.ports.input.CrearCategoriaServicio;
import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataCategoriaRepositorio;
import org.springframework.stereotype.Service;

@Service
public class CrearCategoriaServicioImpl implements CrearCategoriaServicio {

	private final DataCategoriaRepositorio categoriaRepositorio;

	public CrearCategoriaServicioImpl(DataCategoriaRepositorio categoriaRepositorio) {
		this.categoriaRepositorio = categoriaRepositorio;

	}

	@Override
	public void crearCategoria(Categoria categoria) {

		CategoriaEntity categoriaE = new CategoriaEntity();
		categoriaE.setNombre(categoria.getNombre());
		categoriaE.setDescripcion(categoria.getDescripcion());

		categoriaRepositorio.save(categoriaE);

	}
	public boolean categoriaExistePorNombre(String nombre) {
		return categoriaRepositorio.existsByNombre(nombre);
	}
}
