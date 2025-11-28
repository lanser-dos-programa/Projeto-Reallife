package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fichas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FichaDeTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataCriacao = LocalDate.now();
    private String objetivo;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToMany
    @JoinTable(
            name = "ficha_exercicio",
            joinColumns = @JoinColumn(name = "ficha_id"),
            inverseJoinColumns = @JoinColumn(name = "exercicio_id")
    )
    private Set<Exercicio> exercicios = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
}
