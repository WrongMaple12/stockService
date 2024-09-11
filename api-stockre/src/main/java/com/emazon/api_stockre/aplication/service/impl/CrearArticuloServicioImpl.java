package com.emazon.api_stockre.aplication.service.impl;

import com.emazon.api_stockre.domain.model.Articulo;
import com.emazon.api_stockre.domain.ports.input.CrearArticuloServicio;
import com.emazon.api_stockre.infrastructure.entities.ArticuloEntity;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataArticuloRepositorio;
import com.emazon.api_stockre.infrastructure.repositories.DataCategoriaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CrearArticuloServicioImpl implements CrearArticuloServicio {
	private final DataArticuloRepositorio dataArticuloRepositorio;
	private final DataCategoriaRepositorio dataCategoriaRepositorio;

	public CrearArticuloServicioImpl(DataArticuloRepositorio dataArticuloRepositorio, DataCategoriaRepositorio dataCategoriaRepositorio) {
		this.dataArticuloRepositorio = dataArticuloRepositorio;
		this.dataCategoriaRepositorio = dataCategoriaRepositorio;
	}

	@Override
	public void crearArticulo(Articulo articulo) {
		List<CategoriaEntity> categorias = articulo.getCategorias().stream()
			.map(cat -> dataCategoriaRepositorio.findById(cat.getId())
				.orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada: " + cat.getId())))
			.toList();

		if (categorias.size() != articulo.getCategorias().size()) {
			throw new IllegalArgumentException("El artículo debe tener entre 1 y 3 categorías asociadas.");
		}

		ArticuloEntity articuloEntity = new ArticuloEntity();
		articuloEntity.setNombre(articulo.getNombre());
		articuloEntity.setDescripcion(articulo.getDescripcion());
		articuloEntity.setCantidad(articulo.getCantidad());
		articuloEntity.setPrecio(articulo.getPrecio());
		articuloEntity.setCategorias(categorias);

		dataArticuloRepositorio.save(articuloEntity);

	}
}
