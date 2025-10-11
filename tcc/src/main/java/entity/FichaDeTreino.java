package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fichas_treino") // tabela em minúscula
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FichaDeTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;                  // Ex: "Treino de Peito e Braço"
    private LocalDate dataCriacao;        // Data que o treino foi criado
    private LocalDate validade;           // Validade do treino
    private String objetivo;              // Ex: Hipertrofia, Resistência, Condicionamento

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;                  // O aluno que segue essa ficha

    // Relacionamento N:N com Exercícios
    @ManyToMany
    @JoinTable(
            name = "ficha_exercicio",
            joinColumns = @JoinColumn(name = "ficha_id"),
            inverseJoinColumns = @JoinColumn(name = "exercicio_id")
    )
    private Set<Exercicio> exercicios = new HashSet<>();
}
