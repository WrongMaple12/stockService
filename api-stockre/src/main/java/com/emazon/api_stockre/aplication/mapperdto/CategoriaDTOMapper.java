package com.emazon.api_stockre.aplication.mapperdto;
import com.emazon.api_stockre.aplication.dto.CategoriaDTO;
import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaDTOMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "nombre", target = "nombre")
	@Mapping(source = "descripcion", target = "descripcion")
	Categoria toModel(CategoriaEntity categoriaEntity);

	@Mapping(source = "id", target = "id")
	@Mapping(source = "nombre", target = "nombre")
	@Mapping(source = "descripcion", target = "descripcion")
	CategoriaDTO toDTO(CategoriaEntity categoriaEntity);
}

