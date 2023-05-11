package com.foro.backend.services;

import com.foro.backend.models.dtos.CursoDto;
import com.foro.backend.models.entities.Curso;
import com.foro.backend.models.mappers.CursoMapper;
import com.foro.backend.respositories.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl {

    private final ICursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    @Autowired
    public CursoServiceImpl(ICursoRepository cursoRepository, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.cursoMapper = cursoMapper;
    }

    @Transactional
    public CursoDto guardarCurso(CursoDto cursoDto){
        if(cursoDto.id() == null && cursoRepository.existsByNombreAndCategoria(cursoDto.nombre(), cursoDto.categoria())){
            throw new IllegalArgumentException("Ya existe un curso con este nombre y esta categoria");
        }
        return cursoMapper.cursoEntityToCursoDto(
                cursoRepository.save(cursoMapper.cursoDtoToCursoEntity(cursoDto))
        );
    }

    @Transactional(readOnly = true)
    public List<CursoDto> obtenerCursos(){
        return cursoMapper.cursoEntitiesToCursoDtos(
                cursoRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public CursoDto obtenerCursoPorId(long id){
        Optional<Curso> curso = cursoRepository.findById(id);
        if(curso.isEmpty()){
            throw new IllegalArgumentException("No se encuentra el curso solicitado");
        }
        return cursoMapper.cursoEntityToCursoDto( curso.get() );
    }

    @Transactional
    public CursoDto actualizarCurso(long id, CursoDto cursoDto){
        Optional<Curso> curso = cursoRepository.findById(id);
        if(curso.isEmpty()){
            throw new IllegalArgumentException("No se encuentra el curso solicitado");
        }
        cursoDto = cursoDto.withId(id);
        return guardarCurso(cursoDto);
    }

    @Transactional
    public String eliminarCurso(long id){
        Optional<Curso> curso = cursoRepository.findById(id);
        if(curso.isEmpty()){
            throw new IllegalArgumentException("No se encuentra el curso solicitado");
        }
        cursoRepository.delete(curso.get());
        return "Tópico eliminado exitosamente";
    }
}
