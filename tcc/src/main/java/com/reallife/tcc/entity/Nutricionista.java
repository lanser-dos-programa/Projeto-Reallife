package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "nutricionistas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nutricionista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "registro_profissional")
    private String registroProfissional;

    private String Nome;
    private String formacao;
    private String especialidade;
    private String telefone;
    private String experiencia;

    @Builder.Default
    private boolean ativo = true;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "nutricionista")
    private List<Aluno> alunos;

    @OneToMany(mappedBy = "nutricionista", cascade = CascadeType.ALL)
    private List<Dieta> dietas;

    // AGORA CORRETO - O CAMPO 'nutricionista' EXISTE NA ENTIDADE Alimentos
    @OneToMany(mappedBy = "nutricionista", cascade = CascadeType.ALL)
    private Set<Alimentos> alimentosCriados;
}