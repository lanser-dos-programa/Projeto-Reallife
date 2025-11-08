package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "fichas_treino")
@Data
@NoArgsConstructor
public class FichaDeTreino {

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
    @JoinColumn(name = "professor_id")
    private Professor professor;
}