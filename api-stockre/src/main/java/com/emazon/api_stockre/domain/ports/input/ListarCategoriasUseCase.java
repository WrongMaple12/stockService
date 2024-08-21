package com.emazon.api_stockre.domain.ports.input;
import com.emazon.api_stockre.aplication.dto.CategoriaDTO;

import java.util.List;

public interface ListarCategoriasUseCase {
	List<CategoriaDTO> listarCategorias(int pagina, int tamano, String ordenarPor, String direccion);
}
