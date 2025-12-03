package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "dietas")
@Data
@NoArgsConstructor
public class Dieta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String objetivo;
    private boolean ativa = true;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionista;

    @ManyToMany
    @JoinTable(
            name = "dieta_alimentos",
            joinColumns = @JoinColumn(name = "dieta_id"),
            inverseJoinColumns = @JoinColumn(name = "alimento_id")
    )
    private List<Alimentos> alimentos;
}