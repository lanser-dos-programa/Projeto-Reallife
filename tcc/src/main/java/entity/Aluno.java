package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
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
    private String cpf;
    private String telefone;
    private String objetivo; // Ex.: Hipertrofia, Resistência
    private boolean statusAtivo;
    public boolean planoNutricionalAtivo;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionista;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Dietas> dietas;

    // Relacionamento com fichas de treino
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private Set<FichaDeTreino> fichas;

}
