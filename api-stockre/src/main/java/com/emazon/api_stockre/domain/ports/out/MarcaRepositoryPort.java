package com.emazon.api_stockre.domain.ports.out;

import com.emazon.api_stockre.domain.model.Marca;
import com.emazon.api_stockre.domain.model.PaginaMarca;

public interface MarcaRepositoryPort {
	boolean existsByNombre(String nombre);
	PaginaMarca<Marca> findAll(int pagina, int tamano, String direccionOrden, String campoOrden);
}
