package com.reallife.tcc.dto;

import lombok.Data;

import java.util.List;

@Data
public class FichaDto {

    private String nome;
    private String objetivo;
    private Long alunoId;

    private List<Long> exerciciosIds; // IDs dos exercícios escolhidos
}
