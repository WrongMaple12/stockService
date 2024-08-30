package com.emazon.api_stockre.aplication.service.impl;

import com.emazon.api_stockre.domain.model.Marca;
import com.emazon.api_stockre.domain.ports.input.CrearMarcaServicio;
import com.emazon.api_stockre.infrastructure.entities.MarcaEntity;
import com.emazon.api_stockre.infrastructure.repositories.DataMarcaRepositorio;
import org.springframework.stereotype.Service;

@Service
public class CrearMarcaServicioImpl implements CrearMarcaServicio {
	private final DataMarcaRepositorio marcaRepositorio;

	public CrearMarcaServicioImpl(DataMarcaRepositorio marcaRepositorio) {
		this.marcaRepositorio = marcaRepositorio;
	}

	public void crearMarca(Marca marca) {

		MarcaEntity marcaE = new MarcaEntity();
		marcaE.setNombre(marca.getNombre());
		marcaE.setDescripcion(marca.getDescripcion());

		marcaRepositorio.save(marcaE);

	}
	public boolean categoriaExistePorNombre(String nombre) {
		return marcaRepositorio.existsByNombre(nombre);
	}
}

