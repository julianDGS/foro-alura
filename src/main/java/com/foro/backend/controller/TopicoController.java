package com.foro.backend.controller;

import com.foro.backend.exceptions.ErrorFieldException;
import com.foro.backend.models.dtos.TopicoDto;
import com.foro.backend.models.dtos.TopicoResponseDto;
import com.foro.backend.services.TopicoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoServiceImpl topicoService;

    @Autowired
    public TopicoController(TopicoServiceImpl topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<TopicoDto> createTopic(@RequestBody @Valid TopicoDto topicoDto, BindingResult br){
        if(br.hasErrors()){
            throw new ErrorFieldException("Invalid data", br);
        }
        return ResponseEntity.ok().body(topicoService.guardarTopico(topicoDto));
    }

    @GetMapping
    public ResponseEntity<List<TopicoResponseDto>> getTopics(){
        return ResponseEntity.ok().body(topicoService.obtenerTopicos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDto> getTopic( @PathVariable long id){
        return ResponseEntity.ok().body(topicoService.obtenerTopicoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDto> actualizar( @PathVariable long id, @RequestBody @Valid TopicoDto topicoDto, BindingResult br){
        if(br.hasErrors()){
            throw new ErrorFieldException("Invalid data", br);
        }
        return ResponseEntity.ok().body(topicoService.actualizarTopico(id, topicoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTopic( @PathVariable long id){
        return ResponseEntity.ok().body(topicoService.eliminarTopico(id));
    }
}
