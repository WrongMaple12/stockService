package com.emazon.api_stockre.domain.ports.out;

public interface MarcaRepositoryPort {
	boolean existsByNombre(String nombre);
}
