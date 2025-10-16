package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "nutricionistas")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Nutricionista extends Usuario {

    @Column(unique = true, nullable = false)
    private String registroProfissional;   // Exemplo: CRN

    private String formacao;

    // Alunos atendidos
    @OneToMany(mappedBy = "nutricionista", cascade = CascadeType.ALL)
    private List<Aluno> alunos;

    // Dietas criadas pelo nutricionista
    @OneToMany(mappedBy = "nutricionista", cascade = CascadeType.ALL)
    private List<Dieta> dietas;

    // Caso tu tenha uma entidade Alimentos
    @OneToMany(mappedBy = "nutricionista", cascade = CascadeType.ALL)
    private Set<Alimentos> alimentosCriados;
}
