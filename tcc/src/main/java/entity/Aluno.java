package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Entity
@Table(name = "alunos")
@Data
@EqualsAndHashCode(callSuper = true) //herança
public class Aluno extends Usuario {

    private Integer idade;
    private Double peso;
    private Double altura;
    private String matricula;
    private String Cpf;
    private String objetivo; // Ex.: Hipertrofia, Resistência

    // Relacionamento com fichas de treino
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private Set<FichaDeTreino> fichas;
}
