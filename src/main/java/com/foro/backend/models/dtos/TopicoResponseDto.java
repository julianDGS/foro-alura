package com.foro.backend.models.dtos;

import com.foro.backend.models.enums.StatusTopico;

import java.time.LocalDateTime;

public record TopicoResponseDto(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        UsuarioDto autor,
        CursoDto curso
    )
{}
