package com.reallife.tcc.repository;

import com.reallife.tcc.entity.FichaDeTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FichaRepository extends JpaRepository<FichaDeTreino, Long> {

    // Buscar ficha ativa por aluno
    @Query("SELECT f FROM FichaDeTreino f WHERE f.aluno.id = :alunoId AND f.ativa = true")
    Optional<FichaDeTreino> findFichaAtivaByAlunoId(@Param("alunoId") Long alunoId);

    // Buscar todas as fichas por aluno
    List<FichaDeTreino> findAllByAlunoId(Long alunoId);

    // Buscar todas as fichas por aluno ordenadas por data de criação (decrescente)
    List<FichaDeTreino> findAllByAlunoIdOrderByDataCriacaoDesc(Long alunoId);

    // Verificar se existe ficha ativa por aluno
    @Query("SELECT COUNT(f) > 0 FROM FichaDeTreino f WHERE f.aluno.id = :alunoId AND f.ativa = true")
    boolean existsFichaAtivaByAlunoId(@Param("alunoId") Long alunoId);

    // Verificar se existe ficha por aluno
    boolean existsByAlunoId(Long alunoId);

    // Buscar ficha por aluno (retorna uma ficha)
    Optional<FichaDeTreino> findByAlunoId(Long alunoId);

    // Buscar fichas por professor através do aluno
    @Query("SELECT f FROM FichaDeTreino f WHERE f.aluno.professor.id = :professorId")
    List<FichaDeTreino> findByAlunoProfessorId(@Param("professorId") Long professorId);

    // Buscar fichas por exercício
    @Query("SELECT f FROM FichaDeTreino f JOIN f.exercicios e WHERE e.id = :exercicioId")
    List<FichaDeTreino> findByExercicioId(@Param("exercicioId") Long exercicioId);

    // Buscar fichas por nome do exercício
    @Query("SELECT f FROM FichaDeTreino f JOIN f.exercicios e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :nomeExercicio, '%'))")
    List<FichaDeTreino> findByNomeExercicioContaining(@Param("nomeExercicio") String nomeExercicio);

    // Buscar fichas por objetivo (contém)
    List<FichaDeTreino> findByObjetivoContainingIgnoreCase(String objetivo);

    // Buscar fichas recentes
    List<FichaDeTreino> findTop10ByOrderByDataCriacaoDesc();

    // Buscar fichas por aluno e período de data
    @Query("SELECT f FROM FichaDeTreino f WHERE f.aluno.id = :alunoId " +
            "AND f.dataCriacao BETWEEN :inicio AND :fim " +
            "ORDER BY f.dataCriacao DESC")
    List<FichaDeTreino> findByAlunoIdAndDataCriacaoBetween(
            @Param("alunoId") Long alunoId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);

    // Buscar fichas por aluno antes de uma data
    @Query("SELECT f FROM FichaDeTreino f WHERE f.aluno.id = :alunoId " +
            "AND f.dataCriacao < :dataLimite " +
            "ORDER BY f.dataCriacao DESC")
    List<FichaDeTreino> findByAlunoIdAndDataCriacaoBefore(
            @Param("alunoId") Long alunoId,
            @Param("dataLimite") LocalDateTime dataLimite);

    // Buscar fichas por aluno depois de uma data
    @Query("SELECT f FROM FichaDeTreino f WHERE f.aluno.id = :alunoId " +
            "AND f.dataCriacao > :dataLimite " +
            "ORDER BY f.dataCriacao DESC")
    List<FichaDeTreino> findByAlunoIdAndDataCriacaoAfter(
            @Param("alunoId") Long alunoId,
            @Param("dataLimite") LocalDateTime dataLimite);
}