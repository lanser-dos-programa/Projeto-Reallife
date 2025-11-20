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

    // Buscar dieta por aluno
    List<Dieta> findByAlunoId(Long alunoId);
    Optional<Dieta> findByAlunoIdAndAtivaTrue(Long alunoId);

    // Buscar dietas por nutricionista
    List<Dieta> findByNutricionistaId(Long nutricionistaId);
    List<Dieta> findByNutricionistaIdAndAtivaTrue(Long nutricionistaId);

    // Buscar dietas ativas
    List<Dieta> findByAtivaTrue();
    List<Dieta> findByAtivaFalse();

    // Buscar dietas por objetivo
    List<Dieta> findByObjetivo(String objetivo);
    List<Dieta> findByObjetivoContainingIgnoreCase(String objetivo);

    // Buscar dietas por período
    @Query("SELECT d FROM Dieta d WHERE d.dataInicio BETWEEN :startDate AND :endDate")
    List<Dieta> findByPeriodo(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);

    // Buscar dietas ativas na data atual
    @Query("SELECT d FROM Dieta d WHERE d.dataInicio <= :data AND d.dataFim >= :data AND d.ativa = true")
    List<Dieta> findDietasAtivasNaData(@Param("data") LocalDate data);

    // Buscar dieta ativa por aluno
    @Query("SELECT d FROM Dieta d WHERE d.aluno.id = :alunoId AND d.ativa = true")
    Optional<Dieta> findDietaAtivaPorAluno(@Param("alunoId") Long alunoId);

    // Contar dietas
    long countByAtivaTrue();
    long countByAtivaFalse();
    long countByNutricionistaId(Long nutricionistaId);
    long countByAlunoId(Long alunoId);

    // Buscar dietas com alimentos
    @Query("SELECT d FROM Dieta d JOIN FETCH d.alimentos WHERE d.id = :id")
    Optional<Dieta> findByIdWithAlimentos(@Param("id") Long id);

    // Buscar últimas dietas
    @Query("SELECT d FROM Dieta d ORDER BY d.dataInicio DESC")
    List<Dieta> findAllOrderByDataInicioDesc();

    // Buscar top 10 dietas recentes
    @Query(value = "SELECT * FROM dietas ORDER BY data_inicio DESC LIMIT 10", nativeQuery = true)
    List<Dieta> findTop10Recent();

    // Verificar se aluno tem dieta ativa
    boolean existsByAlunoIdAndAtivaTrue(Long alunoId);
}