package com.reallife.tcc.dto;

import lombok.Data;

@Data
public class RegistroDto {
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String role; // ADMIN, ALUNO, NUTRICIONISTA, etc.
}
