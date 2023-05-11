package com.foro.backend.models.mappers;

import com.foro.backend.models.dtos.CursoDto;
import com.foro.backend.models.entities.Curso;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    Curso cursoDtoToCursoEntity(CursoDto cursoDto);
    CursoDto cursoEntityToCursoDto(Curso curso);
    List<CursoDto> cursoEntitiesToCursoDtos(List<Curso> cursos);
}
