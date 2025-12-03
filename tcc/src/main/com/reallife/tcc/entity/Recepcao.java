package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "recepcao")
@Data
@NoArgsConstructor
public class Recepcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String endereco;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    private boolean ativo = true;

    // Senha direta (modo legado - ideal é usar Usuario)
    private String senha;

    // Relacionamento com Usuario (para autenticação unificada)
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String funcao;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }
}