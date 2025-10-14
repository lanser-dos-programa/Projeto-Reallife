package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "alimentos") // tabela em minúscula
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alimentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double calorias;
    private double proteinas;
    private double carboidratos;
    private double gorduras;

    @ManyToMany(mappedBy = "alimentos")
    private List<Dietas> dietas;

}
