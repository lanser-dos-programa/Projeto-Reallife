package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recepcao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recepcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome completo do funcionário da recepção
    @Column(nullable = false)
    private String nome;

    // E-mail de login
    @Column(nullable = false, unique = true)
    private String email;

    // Senha
    @Column(nullable = false)
    private String senha;

    // Telefone de contato (opcional)
    private String telefone;

    // Campo pra status (ativo/inativo)
    @Builder.Default
    private boolean ativo = true;


    @Builder.Default
    private String cargo = "Recepcionista";
}
