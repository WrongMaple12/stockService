package com.emazon.api_stockre.infrastructure.mappers;

import com.emazon.api_stockre.aplication.dto.CategoriaDTO;
import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface CategoriaMapper {

	@Mapping(source = "nombre", target = "nombre")
	@Mapping(source = "descripcion", target = "descripcion")
	CategoriaEntity toeEntity(CategoriaDTO categoriaDTO);

	@Mapping(source = "nombre", target = "nombre")
	@Mapping(source = "descripcion", target = "descripcion")
	CategoriaDTO toDTO(CategoriaEntity categoriaEntity);
	@Mapping(source = "nombre", target = "nombre")
	@Mapping(source = "descripcion", target = "descripcion")
	Categoria toModel(CategoriaEntity categoriaEntity);



}
