package repository;

import entity.Nutricionista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long> {
}
