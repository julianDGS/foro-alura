package com.foro.backend.services;

import com.foro.backend.models.dtos.TopicoDto;
import com.foro.backend.models.dtos.TopicoResponseDto;
import com.foro.backend.models.entities.Topico;
import com.foro.backend.models.mappers.CursoMapper;
import com.foro.backend.models.mappers.TopicoMapper;
import com.foro.backend.models.mappers.UsuarioMapper;
import com.foro.backend.respositories.ITopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoServiceImpl {

    private final ITopicoRepository topicoRepository;
    private final TopicoMapper topicoMapper;
    private final CursoMapper cursoMapper;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioServiceImpl usuarioService;
    private final CursoServiceImpl cursoService;

    @Autowired
    public TopicoServiceImpl(ITopicoRepository topicoRepository, TopicoMapper topicoMapper, CursoMapper cursoMapper, UsuarioMapper usuarioMapper, UsuarioServiceImpl usuarioService, CursoServiceImpl cursoService) {
        this.topicoRepository = topicoRepository;
        this.topicoMapper = topicoMapper;
        this.cursoMapper = cursoMapper;
        this.usuarioMapper = usuarioMapper;
        this.usuarioService = usuarioService;
        this.cursoService = cursoService;
    }

    @Transactional
    public TopicoDto guardarTopico(TopicoDto topicoDto){
        if(topicoDto.id() == null && (topicoRepository.existsByTitulo(topicoDto.titulo()) || topicoRepository.existsByMensaje(topicoDto.mensaje()))){
            throw new IllegalArgumentException("Ya existe un tópico con este título y/o mensaje");
        }
        Topico topico = topicoMapper.topicoDtoToTopicoEntity(topicoDto);
        if(topicoDto.autor().id() == null){
           topico.setAutor( usuarioMapper.usuarioDtoToUsuarioEntity( usuarioService.guardarUsuario(topicoDto.autor()) ));
        }
        if(topicoDto.curso().id() == null){
            topico.setCurso( cursoMapper.cursoDtoToCursoEntity(cursoService.guardarCurso(topicoDto.curso())) );
        }
        return topicoMapper.topicoEntityToTopicoDto(
                topicoRepository.save(topico)
        );
    }

    @Transactional(readOnly = true)
    public List<TopicoResponseDto> obtenerTopicos(){
        return topicoMapper.topicoEntitiesToTopicoResponseDtos(
                topicoRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public TopicoResponseDto obtenerTopicoPorId(long id){
        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isEmpty()){
            throw new IllegalArgumentException("No se encuentra el tópico solicitado");
        }
        return topicoMapper.topicoEntityToTopicoResponseDto( topico.get() );
    }

    @Transactional
    public TopicoDto actualizarTopico(long id, TopicoDto topicoDto){
        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isEmpty()){
            throw new IllegalArgumentException("No se encuentra el tópico solicitado");
        }
        topicoDto = topicoDto.withId(id);
        return guardarTopico(topicoDto);
    }

    @Transactional
    public String eliminarTopico(long id){
        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isEmpty()){
            throw new IllegalArgumentException("No se encuentra el tópico solicitado");
        }
        topicoRepository.delete(topico.get());
        return "Tópico eliminado exitosamente";
    }
}
