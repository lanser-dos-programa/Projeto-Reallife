package com.reallife.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlunoDto {
    private Long id;
    private Integer idade;
    private Double peso;
    private Double altura;
    private String matricula;
    private String telefone;
    private String objetivo;
    private boolean statusAtivo;
    private boolean planoNutricionalAtivo;

    // Dados do usuário
    private String nome;
    private String email;
    private String cpf;

    // IDs dos relacionamentos
    private Long usuarioId;
    private Long nutricionistaId;
    private Long professorId;

    // Nomes para exibição
    private String nomeNutricionista;
    private String nomeProfessor;
}