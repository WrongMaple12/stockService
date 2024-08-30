package com.emazon.api_stockre.infrastructure.adapters;

import com.emazon.api_stockre.domain.ports.out.MarcaRepositoryPort;
import com.emazon.api_stockre.infrastructure.repositories.DataMarcaRepositorio;

public class MarcaRepositorioAdapter implements MarcaRepositoryPort {
	private DataMarcaRepositorio dataMarcaRepositorio;
	@Override
	public boolean existsByNombre(String nombre) {
		return dataMarcaRepositorio.existsByNombre(nombre);
	}
}
