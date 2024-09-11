package com.emazon.api_stockre.domain.ports.out;

import com.emazon.api_stockre.domain.model.Articulo;

public interface ArticuloRepositoryPort {
	void save(Articulo articulo);
	Articulo obtenerPorId(Long id);
}
