package com.foro.backend.controller;

import com.foro.backend.exceptions.ErrorFieldException;
import com.foro.backend.models.dtos.CursoDto;
import com.foro.backend.services.CursoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoServiceImpl cursoService;

    @Autowired
    public CursoController(CursoServiceImpl cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<CursoDto> createTopic(@RequestBody @Valid CursoDto cursoDto, BindingResult br){
        if(br.hasErrors()){
            throw new ErrorFieldException("Invalid data", br);
        }
        return ResponseEntity.ok().body(cursoService.guardarCurso(cursoDto));
    }

    @GetMapping
    public ResponseEntity<List<CursoDto>> getTopics(){
        return ResponseEntity.ok().body(cursoService.obtenerCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDto> getTopic( @PathVariable long id){
        return ResponseEntity.ok().body(cursoService.obtenerCursoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDto> actualizar( @PathVariable long id, @RequestBody @Valid CursoDto cursoDto, BindingResult br){
        if(br.hasErrors()){
            throw new ErrorFieldException("Invalid data", br);
        }
        return ResponseEntity.ok().body(cursoService.actualizarCurso(id, cursoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTopic( @PathVariable long id){
        return ResponseEntity.ok().body(cursoService.eliminarCurso(id));
    }
}
