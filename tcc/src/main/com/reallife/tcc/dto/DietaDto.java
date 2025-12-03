package com.reallife.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietaDto {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String objetivo;
    private boolean ativa;

    // IDs para relacionamentos
    private Long alunoId;
    private Long nutricionistaId;
    private List<Long> alimentosIds;

    // Nomes para exibição
    private String alunoNome;
    private String nutricionistaNome;
    private List<String> alimentosNomes;

    // Estatísticas calculadas
    private Double totalCalorias;
    private Double totalProteinas;
    private Double totalCarboidratos;
    private Double totalGorduras;
    private Integer totalAlimentos;
}