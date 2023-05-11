package com.foro.backend.respositories;

import com.foro.backend.models.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNombreAndCategoria(String nombre, String categoria);
}
