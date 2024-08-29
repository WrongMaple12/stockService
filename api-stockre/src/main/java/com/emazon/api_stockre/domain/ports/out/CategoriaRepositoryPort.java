package com.emazon.api_stockre.domain.ports.out;


import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.domain.model.Pagina;


public interface CategoriaRepositoryPort {
	boolean existsByNombre(String nombre);
	Pagina<Categoria> findAll(int pagina, int tamano, String direccionOrden, String campoOrden);
	}
