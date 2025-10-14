package repository;

import entity.Dietas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietasRepository extends JpaRepository<Dietas, Long> {
}
