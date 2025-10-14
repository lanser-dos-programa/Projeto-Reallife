package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "professores")
@Data
@EqualsAndHashCode(callSuper = true) // herança
public class Nutricionista extends Usuario {

    @Column(unique = true, nullable = false)
    private String cpf;

    private String formacao;               // Formação a
    private String registroProfissional;   // Ex.: CRN

    @OneToMany(mappedBy = "nutricionista", cascade = CascadeType.ALL)
    private List<Aluno> alunos;

    @OneToMany(mappedBy = "nutricionista", cascade = CascadeType.ALL)
    private List<Dietas> dietas;

    // Relacionamento com dieta
    @OneToMany(mappedBy = "Nutricionista", cascade = CascadeType.ALL)
    private Set<Alimentos> dietasCriadas;
}
