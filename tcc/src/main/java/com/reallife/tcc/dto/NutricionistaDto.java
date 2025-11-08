package com.reallife.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutricionistaDto {
    private Long id;
    private String registroProfissional;
    private String formacao;
    private String especialidade;
    private String telefone;
    private String experiencia;
    private boolean ativo;

    // Dados do usuário
    private String nome;
    private String email;
    private String cpf;

    private Long usuarioId;

    // Estatísticas
    private Integer totalAlunos;
    private Integer totalDietas;
}