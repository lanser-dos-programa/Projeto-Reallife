package repository;

import entity.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietaRepository extends JpaRepository<Dieta, Long> {
    Dieta findByAlunoId(Long alunoId);
}