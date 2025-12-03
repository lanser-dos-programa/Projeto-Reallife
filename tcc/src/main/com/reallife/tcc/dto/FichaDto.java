package com.reallife.tcc.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FichaDto {
    private Long id;
    private String nome;
    private String objetivo;
    private Boolean ativa;
    private LocalDateTime dataCriacao;
    private Long alunoId;
    private Long professorId;
    private List<Long> exerciciosIds;
    private List<ExercicioDto> exercicios;
}