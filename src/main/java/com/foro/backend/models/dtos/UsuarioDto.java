package com.foro.backend.models.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDto(
        Long id,
        @NotBlank
        String nombre,
        @Email
        String email
) {
        public UsuarioDto withId(long id){
                return new UsuarioDto(id, this.nombre, this.email);
        }
}
