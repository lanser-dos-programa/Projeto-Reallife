package com.reallife.tcc.repository;

import com.reallife.tcc.entity.FichaDeTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FichaRepository extends JpaRepository<FichaDeTreino, Long> {

    // Buscar ficha por ID do aluno
    Optional<FichaDeTreino> findByAlunoId(Long alunoId);

    // Buscar fichas por ID do professor (através do aluno)
    @Query("SELECT f FROM FichaDeTreino f WHERE f.aluno.professor.id = :professorId")
    List<FichaDeTreino> findByAlunoProfessorId(@Param("professorId") Long professorId);

    // Buscar fichas ativas por professor
    @Query("SELECT f FROM FichaDeTreino f WHERE f.aluno.professor.id = :professorId AND f.ativa = true")
    List<FichaDeTreino> findByAlunoProfessorIdAndAtivaTrue(@Param("professorId") Long professorId);

    // Verificar se existe ficha para um aluno
    boolean existsByAlunoId(Long alunoId);

    // Buscar fichas por status (ativa/inativa)
    List<FichaDeTreino> findByAtivaTrue();
    List<FichaDeTreino> findByAtivaFalse();

    // Buscar fichas por objetivo
    List<FichaDeTreino> findByObjetivoContainingIgnoreCase(String objetivo);

    // Buscar fichas por nome
    List<FichaDeTreino> findByNomeContainingIgnoreCase(String nome);

    // Contar fichas por professor
    @Query("SELECT COUNT(f) FROM FichaDeTreino f WHERE f.aluno.professor.id = :professorId")
    long countByAlunoProfessorId(@Param("professorId") Long professorId);

    // Contar fichas ativas por professor
    @Query("SELECT COUNT(f) FROM FichaDeTreino f WHERE f.aluno.professor.id = :professorId AND f.ativa = true")
    long countByAlunoProfessorIdAndAtivaTrue(@Param("professorId") Long professorId);

    // Buscar ficha ativa do aluno
    @Query("SELECT f FROM FichaDeTreino f WHERE f.aluno.id = :alunoId AND f.ativa = true")
    Optional<FichaDeTreino> findFichaAtivaByAlunoId(@Param("alunoId") Long alunoId);

    // Buscar todas as fichas de um aluno
    List<FichaDeTreino> findAllByAlunoId(Long alunoId);

    // Buscar fichas recentes (ordenadas por data de criação)
    List<FichaDeTreino> findTop10ByOrderByDataCriacaoDesc();

    // Buscar fichas por professor com paginação
    @Query("SELECT f FROM FichaDeTreino f WHERE f.aluno.professor.id = :professorId ORDER BY f.dataCriacao DESC")
    List<FichaDeTreino> findByProfessorIdOrderByDataCriacaoDesc(@Param("professorId") Long professorId);

    // Verificar se aluno tem ficha ativa
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FichaDeTreino f WHERE f.aluno.id = :alunoId AND f.ativa = true")
    boolean existsFichaAtivaByAlunoId(@Param("alunoId") Long alunoId);

    // Buscar fichas por lista de IDs de alunos
    @Query("SELECT f FROM FichaDeTreino f WHERE f.aluno.id IN :alunoIds")
    List<FichaDeTreino> findByAlunoIdIn(@Param("alunoIds") List<Long> alunoIds);

    // Buscar fichas com exercícios específicos
    @Query("SELECT DISTINCT f FROM FichaDeTreino f JOIN f.exercicios e WHERE e.id = :exercicioId")
    List<FichaDeTreino> findByExercicioId(@Param("exercicioId") Long exercicioId);

    // Estatísticas - total de fichas criadas por período
    @Query("SELECT COUNT(f) FROM FichaDeTreino f WHERE f.dataCriacao BETWEEN :dataInicio AND :dataFim")
    long countFichasCriadasNoPeriodo(@Param("dataInicio") java.time.LocalDateTime dataInicio,
                                     @Param("dataFim") java.time.LocalDateTime dataFim);

    // Buscar fichas que contêm determinado exercício
    @Query("SELECT f FROM FichaDeTreino f JOIN f.exercicios e WHERE e.nome LIKE %:nomeExercicio%")
    List<FichaDeTreino> findByNomeExercicioContaining(@Param("nomeExercicio") String nomeExercicio);
}