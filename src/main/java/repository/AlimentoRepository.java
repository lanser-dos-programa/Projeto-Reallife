package repository;

import entity.Alimentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimentos, Long> {
}
