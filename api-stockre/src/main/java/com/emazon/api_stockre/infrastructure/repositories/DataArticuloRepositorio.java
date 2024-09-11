package com.emazon.api_stockre.infrastructure.repositories;

import com.emazon.api_stockre.infrastructure.entities.ArticuloEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataArticuloRepositorio extends JpaRepository<ArticuloEntity, Long> {
}
