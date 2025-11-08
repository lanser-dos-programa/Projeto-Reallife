package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Dieta;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SpringBootApplication
public interface DietaRepository extends JpaRepository<Dieta, Long> {
    Dieta findByAlunoId(Long alunoId);
}