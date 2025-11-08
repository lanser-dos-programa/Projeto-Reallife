package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DietaRepository extends JpaRepository<Dieta, Long> {

    Optional<Dieta> findByAlunoId(Long alunoId);
    List<Dieta> findByNutricionistaId(Long nutricionistaId);

    List<Dieta> findByAtivaTrue();
    List<Dieta> findByAtivaFalse();

    List<Dieta> findByObjetivo(String objetivo);
    List<Dieta> findByObjetivoContainingIgnoreCase(String objetivo);

    @Query("SELECT d FROM Dieta d WHERE d.dataInicio <= :data AND d.dataFim >= :data AND d.ativa = true")
    List<Dieta> findDietasAtivasNaData(@Param("data") LocalDate data);

    @Query("SELECT d FROM Dieta d WHERE d.aluno.id = :alunoId AND d.ativa = true")
    Optional<Dieta> findDietaAtivaPorAluno(@Param("alunoId") Long alunoId);

    @Query("SELECT d FROM Dieta d WHERE d.nutricionista.id = :nutricionistaId AND d.ativa = true")
    List<Dieta> findDietasAtivasPorNutricionista(@Param("nutricionistaId") Long nutricionistaId);

    @Query("SELECT d FROM Dieta d WHERE d.dataInicio BETWEEN :startDate AND :endDate")
    List<Dieta> findDietasPorPeriodo(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    long countByAtivaTrue();
    long countByAtivaFalse();
    long countByNutricionistaId(Long nutricionistaId);

    @Query("SELECT COUNT(d) FROM Dieta d WHERE d.aluno.id = :alunoId")
    long countByAlunoId(@Param("alunoId") Long alunoId);

    @Query("SELECT d FROM Dieta d ORDER BY d.dataInicio DESC")
    List<Dieta> findAllOrderByDataInicioDesc();

    @Query("SELECT d FROM Dieta d WHERE d.aluno.nutricionista.id = :nutricionistaId")
    List<Dieta> findDietasPorNutricionistaAlunos(@Param("nutricionistaId") Long nutricionistaId);

    @Query(value = "SELECT * FROM dietas WHERE ativa = true ORDER BY data_inicio DESC LIMIT 10", nativeQuery = true)
    List<Dieta> findTop10Ativas();
}