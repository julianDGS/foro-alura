package com.foro.backend.models.mappers;

import com.foro.backend.models.dtos.TopicoDto;
import com.foro.backend.models.dtos.TopicoResponseDto;
import com.foro.backend.models.entities.Topico;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicoMapper {

    Topico topicoDtoToTopicoEntity(TopicoDto topicoDto);
    TopicoDto topicoEntityToTopicoDto(Topico topico);
    TopicoResponseDto topicoEntityToTopicoResponseDto(Topico topico);
    List<TopicoResponseDto> topicoEntitiesToTopicoResponseDtos(List<Topico> topicos);
}
