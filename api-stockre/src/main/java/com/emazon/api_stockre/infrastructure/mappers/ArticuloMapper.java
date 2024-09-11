package com.emazon.api_stockre.infrastructure.mappers;


import com.emazon.api_stockre.domain.model.Articulo;
import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.infrastructure.entities.ArticuloEntity;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticuloMapper {
	@Mapping(source = "categorias", target = "categorias", qualifiedByName = "entitiesToModel")
	Articulo toModel(ArticuloEntity articuloEntity);


	@Mapping(source = "categorias", target = "categorias", qualifiedByName = "modelToEntities")
	ArticuloEntity toEntity(Articulo articulo);

	@Named("entitiesToModel")
	default List<Categoria> entitiesToModel(List<CategoriaEntity> categorias) {
		return categorias.stream()
			.map(entity -> new Categoria(entity.getId(), entity.getNombre(), entity.getDescripcion()))
			.toList();
	}

	@Named("modelToEntities")
	default List<CategoriaEntity> modelToEntities(List<Categoria> categorias) {
		return categorias.stream()
			.map(model -> {
				CategoriaEntity entity = new CategoriaEntity();
				entity.setId(model.getId());
				entity.setNombre(model.getNombre());
				entity.setDescripcion(model.getDescripcion());
				return entity;
			})
			.toList();
	}
}
