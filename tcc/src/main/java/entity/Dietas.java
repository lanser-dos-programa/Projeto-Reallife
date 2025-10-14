package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "dietas") // tabela em minúscula
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dietas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tipo;                  // Ex: Bulking, Cuting
    private String objetivo;              // Ex: Ganho de massa, emagrecimento

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
