package repository;

import entity.Recepcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecepcaoRepository extends JpaRepository<Recepcao, Long> {

    // Buscar funcionário da recepção pelo e-mail (útil pra login)
    Optional<Recepcao> findByEmail(String email);

    // Verificar se já existe e-mail cadastrado
    boolean existsByEmail(String email);
}
