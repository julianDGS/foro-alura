package com.foro.backend.models.dtos;

import com.foro.backend.models.entities.Curso;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoDto(
    Long id,
    @NotBlank
    String titulo,
    @NotBlank
    String mensaje,
    @NotNull
    @Valid
    UsuarioDto autor,
    @NotNull
    @Valid
    CursoDto curso
    )
{
    public TopicoDto withId(long id){
        return new TopicoDto(id, this.titulo, this.mensaje, this.autor, this.curso);
    }
}
