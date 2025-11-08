package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "alunos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer idade;
    private Double peso;
    private Double altura;
    private String matricula;
    private String telefone;
    private String objetivo;

    @Builder.Default
    private boolean statusAtivo = true;

    @Builder.Default
    private boolean planoNutricionalAtivo = false;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionista;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Dieta> dietas;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private Set<FichaDeTreino> fichas;
}