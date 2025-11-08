package com.reallife.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorDto {
    private Long id;
    private String formacao;
    private String registroProfissional;
    private String especialidade;
    private String telefone;
    private Integer anosExperiencia;
    private boolean ativo;

    // Dados do usuário
    private String nome;
    private String email;
    private String cpf;

    private Long usuarioId;

    // Estatísticas (opcional)
    private Integer totalAlunos;
    private Integer totalFichas;
}