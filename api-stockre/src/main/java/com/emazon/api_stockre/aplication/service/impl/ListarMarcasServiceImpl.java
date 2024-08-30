package com.emazon.api_stockre.aplication.service.impl;
import com.emazon.api_stockre.domain.model.Marca;
import com.emazon.api_stockre.domain.model.PaginaMarca;
import com.emazon.api_stockre.domain.ports.input.ListarMarcasUseCase;
import com.emazon.api_stockre.infrastructure.entities.MarcaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataMarcaRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarMarcasServiceImpl implements ListarMarcasUseCase {
	private final DataMarcaRepositorio marcaRepositorio;

	public ListarMarcasServiceImpl(DataMarcaRepositorio marcaRepositorio) {
		this.marcaRepositorio = marcaRepositorio;
	}


	public PaginaMarca<Marca> listarMarcas(int pagina, int tamano, String direccionOrden, String campoOrden) {
		Sort.Direction sortDirection = "desc".equalsIgnoreCase(direccionOrden) ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(pagina, tamano, Sort.by(sortDirection, campoOrden));

		Page<MarcaEntity> resultPage = marcaRepositorio.findAll(pageable);

		List<Marca> marcas = resultPage.getContent().stream()
			.map(this::convertToDomain)
			.toList();

		return new PaginaMarca<>(
			marcas,
			resultPage.getNumber(),
			resultPage.getSize(),
			resultPage.getTotalPages(),
			resultPage.getTotalElements()
		);
	}

	private Marca convertToDomain(MarcaEntity entity) {
		return new Marca(entity.getId(), entity.getNombre(), entity.getDescripcion());
	}
}

