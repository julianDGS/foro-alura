package com.foro.backend.controller;

import com.foro.backend.exceptions.ErrorFieldException;
import com.foro.backend.models.dtos.UsuarioDto;
import com.foro.backend.services.UsuarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    @Autowired
    public UsuarioController(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> createUser(@RequestBody @Valid UsuarioDto usuarioDto, BindingResult br){
        if(br.hasErrors()){
            throw new ErrorFieldException("Invalid data", br);
        }
        return ResponseEntity.ok().body(usuarioService.guardarUsuario(usuarioDto));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> getUsers(){
        return ResponseEntity.ok().body(usuarioService.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUser( @PathVariable long id){
        return ResponseEntity.ok().body(usuarioService.obtenerUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> updateUser( @PathVariable long id, @RequestBody @Valid UsuarioDto usuarioDto, BindingResult br){
        if(br.hasErrors()){
            throw new ErrorFieldException("Invalid data", br);
        }
        return ResponseEntity.ok().body(usuarioService.actualizarUsuario(id, usuarioDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser( @PathVariable long id){
        return ResponseEntity.ok().body(usuarioService.eliminarUsuario(id));
    }
}
