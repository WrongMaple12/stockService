package com.emazon.api_stockre.infrastructure.adapters;

import com.emazon.api_stockre.domain.model.Articulo;
import com.emazon.api_stockre.domain.ports.out.ArticuloRepositoryPort;
import com.emazon.api_stockre.infrastructure.entities.ArticuloEntity;
import com.emazon.api_stockre.infrastructure.mappers.ArticuloMapper;
import com.emazon.api_stockre.infrastructure.repositories.DataArticuloRepositorio;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticuloRepositorioAdapter implements ArticuloRepositoryPort {
	private DataArticuloRepositorio dataArticuloRepositorio;
	private ArticuloMapper articuloMapper;
	@Override
	public void save(Articulo articulo) {
		ArticuloEntity articuloEntity = articuloMapper.toEntity(articulo);
		dataArticuloRepositorio.save(articuloEntity);
	}

	@Override
	public Articulo obtenerPorId(Long id) {
		Optional<ArticuloEntity> articuloEntityOptional = dataArticuloRepositorio.findById(id);
		return articuloEntityOptional.map(articuloMapper::toModel).orElse(null);
	}
}
