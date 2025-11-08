package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Exercicio;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SpringBootApplication
public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

}