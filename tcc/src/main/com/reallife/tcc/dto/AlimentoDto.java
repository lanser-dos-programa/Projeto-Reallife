package com.reallife.tcc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlimentoDto {
    private Long id;
    private String nome;
    private double calorias;
    private double proteinas;
    private double carboidratos;
    private double gorduras;
    private String categoria;
    private String medidaCaseira;
    private boolean ativo;
    private Long nutricionistaId;
    private String nutricionistaNome;

    // Métodos úteis para cálculos
    public double getCaloriasTotais(double quantidade) {
        return calorias * quantidade;
    }

    public double getProteinasTotais(double quantidade) {
        return proteinas * quantidade;
    }

    public double getCarboidratosTotais(double quantidade) {
        return carboidratos * quantidade;
    }

    public double getGordurasTotais(double quantidade) {
        return gorduras * quantidade;
    }

    // Método para calcular totais baseado na medida caseira
    public double calcularCaloriasPorMedida() {
        // Aqui você pode adicionar lógica para converter medida caseira
        // Por exemplo: "1 xícara" = 200g, etc.
        return calorias; // Retorna valor base por 100g por padrão
    }
}