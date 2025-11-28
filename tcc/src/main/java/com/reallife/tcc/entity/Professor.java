package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "professores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String formacao;
    private String registroProfissional;
    private String especialidade;
    private String telefone;
    private Integer anosExperiencia;

    @Builder.Default
    private Boolean ativo = true;

    // Relacionamento com Usuário
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Relacionamento com Alunos
    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Aluno> alunos = new ArrayList<>();

    // Relacionamento com Fichas (CORREÇÃO: mappedBy deve apontar para "professor" na FichaDeTreino)
    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    @Builder.Default
    private List<FichaDeTreino> fichasCriadas = new ArrayList<>();
}