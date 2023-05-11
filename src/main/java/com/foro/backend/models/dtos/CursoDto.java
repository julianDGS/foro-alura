package com.foro.backend.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record CursoDto(
        Long id,
        @NotBlank
        String nombre,
        @NotBlank
        String categoria
    )
{
    public CursoDto withId(long id){
        return new CursoDto(id, this.nombre, this.categoria);
    }
}
