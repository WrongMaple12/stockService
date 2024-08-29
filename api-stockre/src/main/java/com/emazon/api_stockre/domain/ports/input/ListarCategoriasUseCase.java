package com.emazon.api_stockre.domain.ports.input;

import com.emazon.api_stockre.domain.model.Categoria;
import com.emazon.api_stockre.domain.model.Pagina;


public interface ListarCategoriasUseCase {
	Pagina<Categoria> listarCategorias(int pagina, int tamano, String direccionOrden, String campoOrden);
}
