package com.emazon.api_stockre.aplication.mapperdto;

import com.emazon.api_stockre.aplication.dto.MarcaDTO;
import com.emazon.api_stockre.domain.model.Marca;
import com.emazon.api_stockre.infrastructure.entities.MarcaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface MarcaDTOMapper {
	@Mapping(source = "id", target = "id")
	@Mapping(source = "nombre", target = "nombre")
	@Mapping(source = "descripcion", target = "descripcion")
	Marca toModel(MarcaEntity marcaEntity);

	@Mapping(source = "id", target = "id")
	@Mapping(source = "nombre", target = "nombre")
	@Mapping(source = "descripcion", target = "descripcion")
	MarcaDTO toDTO(MarcaEntity marcaEntity);
}
