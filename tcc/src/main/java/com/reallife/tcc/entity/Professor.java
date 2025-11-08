package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

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

    @Column(name = "registro_profissional")
    private String registroProfissional; // CREF

    private String especialidade;
    private String telefone;
    private Integer anosExperiencia;

    @Builder.Default
    private boolean ativo = true;

    // RELACIONAMENTO COM USUARIO
    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    // FICHAS DE TREINO CRIADAS PELO PROFESSOR
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private Set<FichaDeTreino> fichasCriadas;

    // ALUNOS ORIENTADOS PELO PROFESSOR
    @OneToMany(mappedBy = "professor")
    private Set<Aluno> alunos;
}