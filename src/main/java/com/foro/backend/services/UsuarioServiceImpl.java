package com.foro.backend.services;

import com.foro.backend.models.dtos.UsuarioDto;
import com.foro.backend.models.entities.Usuario;
import com.foro.backend.models.mappers.UsuarioMapper;
import com.foro.backend.respositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl {

    private final IUsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    public UsuarioDto guardarUsuario(UsuarioDto usuarioDto){
        if(usuarioDto.id() == null && usuarioRepository.existsByEmail(usuarioDto.email())){
            throw new IllegalArgumentException("Ya existe un usuario con este email");
        }
        return usuarioMapper.usuarioEntityToUsuarioDto(
                usuarioRepository.save( usuarioMapper.usuarioDtoToUsuarioEntity( usuarioDto ))
        );
    }

    @Transactional(readOnly = true)
    public List<UsuarioDto> obtenerUsuarios(){
        return usuarioMapper.usuarioEntitiesToUsuarioDtos(
                usuarioRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public UsuarioDto obtenerUsuarioPorId(long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()){
            throw new IllegalArgumentException("No se encuentra el usuario solicitado");
        }
        return usuarioMapper.usuarioEntityToUsuarioDto( usuario.get() );
    }

    @Transactional
    public UsuarioDto actualizarUsuario(long id, UsuarioDto usuarioDto){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()){
            throw new IllegalArgumentException("No se encuentra el usuario solicitado");
        }
        usuarioDto = usuarioDto.withId(id);
        return guardarUsuario(usuarioDto);
    }

    @Transactional
    public String eliminarUsuario(long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()){
            throw new IllegalArgumentException("No se encuentra el usuario solicitado");
        }
        usuarioRepository.delete(usuario.get());
        return "Tópico eliminado exitosamente";
    }
}
