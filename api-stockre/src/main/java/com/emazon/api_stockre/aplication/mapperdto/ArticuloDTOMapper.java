package com.emazon.api_stockre.aplication.mapperdto;

import com.emazon.api_stockre.aplication.dto.ArticuloDTO;
import com.emazon.api_stockre.domain.model.Articulo;
import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.infrastructure.entities.ArticuloEntity;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface ArticuloDTOMapper {
	// Convierte de Articulo a ArticuloDTO
	@Mapping(source = "categorias", target = "categoriasIds", qualifiedByName = "categoriasToIdsFromModel")
	ArticuloDTO toDTO(Articulo articulo);

	// Convierte de ArticuloDTO a Articulo
	@Mapping(source = "categoriasIds", target = "categorias", qualifiedByName = "idsToCategorias")
	Articulo toModel(ArticuloDTO articuloDTO);

	// Convierte de ArticuloEntity a ArticuloDTO
	@Mapping(source = "categorias", target = "categoriasIds", qualifiedByName = "entitiesToIds")
	ArticuloDTO toDTO(ArticuloEntity articuloEntity);

	// Convierte de Articulo a ArticuloEntity
	@Mapping(source = "categorias", target = "categorias", qualifiedByName = "modelToEntities")
	ArticuloEntity toEntity(Articulo articulo);

	// Mapea de List<Categoria> a List<Long> (para Articulo a ArticuloDTO)
	@Named("categoriasToIdsFromModel")
	default List<Long> categoriasToIdsFromModel(List<Categoria> categorias) {
		return categorias.stream()
			.map(Categoria::getId)
			.toList();
	}

	// Mapea de List<Long> a List<CategoriaEntity> (para ArticuloDTO a Articulo)
	@Named("idsToCategorias")
	default List<Categoria> idsToCategorias(List<Long> ids) {
		return ids.stream()
			.map(id -> {
				Categoria categoria = new Categoria();
				categoria.setId(id);
				return categoria;
			})
			.toList();
	}

	// Mapea de List<CategoriaEntity> a List<Long> (para ArticuloEntity a ArticuloDTO)
	@Named("entitiesToIds")
	default List<Long> categoriasToIdsFromEntity(List<CategoriaEntity> categorias) {
		return categorias.stream()
			.map(CategoriaEntity::getId)
			.toList();
	}

	// Mapea de List<Categoria> a List<CategoriaEntity> (para Articulo a ArticuloEntity)amed("modelToEntities")
	default List<CategoriaEntity> modelToEntities(List<Categoria> categorias) {
		return categorias.stream()
			.map(categoria -> {
				CategoriaEntity entity = new CategoriaEntity();
				entity.setId(categoria.getId());
				entity.setNombre(categoria.getNombre());
				entity.setDescripcion(categoria.getDescripcion());
				return entity;
			})
			.toList();
	}
}
