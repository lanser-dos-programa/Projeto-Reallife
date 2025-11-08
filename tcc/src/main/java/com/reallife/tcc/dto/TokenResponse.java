package com.reallife.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponse {
    private String token;
    private String tipo; // "Bearer"
    private Long usuarioId;
    private String nome;
    private String email;
    private String role;
    private Long expiresIn;

    // IDs específicos do tipo de usuário
    private Long alunoId;
    private Long professorId;
    private Long nutricionistaId;
}