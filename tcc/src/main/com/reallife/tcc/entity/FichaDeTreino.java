package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fichas_treino")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaDeTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String objetivo;

    @Column(name = "ativa")
    @Builder.Default
    private Boolean ativa = false;

    @Builder.Default
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDateTime dataAtualizacao;

    // Relacionamento com Aluno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    // Relacionamento com Professor (CORREÇÃO: adicionar esta propriedade)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    // Relacionamento com Exercícios
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ficha_exercicios",
            joinColumns = @JoinColumn(name = "ficha_id"),
            inverseJoinColumns = @JoinColumn(name = "exercicio_id")
    )
    @Builder.Default
    private Set<Exercicio> exercicios = new HashSet<>();

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}