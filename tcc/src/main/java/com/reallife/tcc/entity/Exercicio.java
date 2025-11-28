package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exercicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String grupoMuscular; // Peito, Costas, Pernas, Ombros...

    @Column(length = 2000)
    private String descricao;

    @ManyToMany(mappedBy = "exercicios")
    private Set<FichaDeTreino> fichas = new HashSet<>();
}
