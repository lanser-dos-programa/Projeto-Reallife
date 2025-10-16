package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "dietas") // tabela em minúscula
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dieta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tipo;                  // Ex: Bulking, Cuting
    private String objetivo;              // Ex: Ganho de massa, emagrecimento
    private Integer Kcal;                 // Calorias da dieta
    private Integer Refeicoes;            // Numero de refeições

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
