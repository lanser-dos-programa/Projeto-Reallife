package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

    Optional<Exercicio> findByNome(String nome);
    List<Exercicio> findByNomeContainingIgnoreCase(String nome);
    List<Exercicio> findByGrupoMuscular(String grupoMuscular);
    List<Exercicio> findByGrupoMuscularContainingIgnoreCase(String grupoMuscular);
    List<Exercicio> findByAtivoTrue();
}