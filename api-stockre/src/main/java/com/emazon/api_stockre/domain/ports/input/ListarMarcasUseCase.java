package com.emazon.api_stockre.domain.ports.input;

import com.emazon.api_stockre.domain.model.Marca;
import com.emazon.api_stockre.domain.model.PaginaMarca;

public interface ListarMarcasUseCase {
	PaginaMarca<Marca> listarMarcas(int pagina, int tamano, String direccionOrden, String campoOrden);
}
