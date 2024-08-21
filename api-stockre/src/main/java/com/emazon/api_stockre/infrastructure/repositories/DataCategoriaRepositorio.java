package com.emazon.api_stockre.infrastructure.repositories;
import com.emazon.api_stockre.infrastructure.entities.CategoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface DataCategoriaRepositorio extends JpaRepository<CategoriaEntity, Long> {
	boolean existsByNombre(String nombre);
	Page<CategoriaEntity>findAll(Pageable pageable);



}

