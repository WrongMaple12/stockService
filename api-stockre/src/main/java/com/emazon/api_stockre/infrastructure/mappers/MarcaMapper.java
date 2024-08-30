package com.emazon.api_stockre.infrastructure.mappers;

import com.emazon.api_stockre.aplication.dto.MarcaDTO;
import com.emazon.api_stockre.domain.model.Marca;
import com.emazon.api_stockre.infrastructure.entities.MarcaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarcaMapper {
	@Mapping(source = "nombre", target = "nombre")
	@Mapping(source = "descripcion", target = "descripcion")
	MarcaEntity toeEntity(MarcaDTO marcaDTO);

	@Mapping(source = "nombre", target = "nombre")
	@Mapping(source = "descripcion", target = "descripcion")
	MarcaDTO toDTO(MarcaEntity marcaEntity);

	@Mapping(source = "nombre", target = "nombre")
	@Mapping(source = "descripcion", target = "descripcion")
	Marca toModel(MarcaEntity marcaEntity);
}
