package com.reallife.tcc.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExercicioDto {

    private Long id;
    private String nome;
    private String grupoMuscular;
    private String descricao;
}
