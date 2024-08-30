package com.emazon.api_stockre.infrastructure.repositories;

import com.emazon.api_stockre.infrastructure.entities.MarcaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataMarcaRepositorio extends JpaRepository<MarcaEntity, Long> {
	boolean existsByNombre(String nombre);
	Page<MarcaEntity> findAll(Pageable pageable);
}
