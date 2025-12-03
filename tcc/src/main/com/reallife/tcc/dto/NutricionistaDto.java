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

    // DADOS DO NUTRICIONISTA
    private String registroProfissional;
    private String formacao;
    private String especialidade;
    private String telefone;
    private String experiencia;
    private boolean ativo;

    // DADOS DO USUÁRIO VINCULADO
    private Long usuarioId;
    private String nome;
    private String email;
    private String cpf;

    // ESTATÍSTICAS
    private long totalAlunos;
}
