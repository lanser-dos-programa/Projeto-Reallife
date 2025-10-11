package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exercicios") // tabela em minúscula
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;                 // Nome do exercício, ex: "Supino reto"
    private String grupoMuscular;        // Ex: Peito, Costas, Pernas, Braços, Ombro, Abdômen
    private String descricao;            // Breve descrição do exercício

    // Relacionamento N:N com FichaDeTreino
    @ManyToMany(mappedBy = "exercicios")
    private Set<FichaDeTreino> fichas = new HashSet<>();
}
