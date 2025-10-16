package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Entity
@Table(name = "professores")
@Data
@EqualsAndHashCode(callSuper = true) // herança
public class Professor extends Usuario {

    @Column(unique = true, nullable = false)
    private String cpf;

    private String formacao;               // Formação a
    private String registroProfissional;   // Ex.: CREF
    private String especialidade;          // Ex.: Musculação, Crossfit, luta

    // Relacionamento com fichas de treino criadas
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private Set<FichaDeTreino> fichasCriadas;

    // Aulas
    @OneToMany
    private Set<Aluno> aulas;
}
