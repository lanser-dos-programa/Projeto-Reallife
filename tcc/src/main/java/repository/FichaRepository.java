package repository;

import entity.FichaDeTreino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaRepository extends JpaRepository<FichaDeTreino, Long> {

}