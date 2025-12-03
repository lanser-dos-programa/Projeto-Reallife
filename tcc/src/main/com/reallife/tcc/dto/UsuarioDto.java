package com.reallife.tcc.dto;

import com.reallife.tcc.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private Role role;
    private boolean ativo;
    private String dataCriacao;
}