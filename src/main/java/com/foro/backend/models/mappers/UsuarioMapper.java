package com.foro.backend.models.mappers;

import com.foro.backend.models.dtos.UsuarioDto;
import com.foro.backend.models.entities.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario usuarioDtoToUsuarioEntity(UsuarioDto usuarioDto);
    UsuarioDto usuarioEntityToUsuarioDto(Usuario usuario);
    List<UsuarioDto> usuarioEntitiesToUsuarioDtos(List<Usuario> usuarios);
}
