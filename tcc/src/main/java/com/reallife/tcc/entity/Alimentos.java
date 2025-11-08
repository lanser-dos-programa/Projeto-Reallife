package com.reallife.tcc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "alimentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alimentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double calorias;
    private double proteinas;
    private double carboidratos;
    private double gorduras;
    private String categoria;
    private String medidaCaseira;
    private boolean ativo = true;

    // CAMPO ADICIONADO PARA RELACIONAMENTO COM NUTRICIONISTA
    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionista;

    @ManyToMany(mappedBy = "alimentos")
    private List<Dieta> dietas;
}