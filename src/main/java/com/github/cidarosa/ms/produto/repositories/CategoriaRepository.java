package com.github.cidarosa.ms.produto.repositories;

import com.github.cidarosa.ms.produto.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}
