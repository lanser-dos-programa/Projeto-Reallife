package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    // Buscar por status do plano nutricional
    List<Aluno> findByPlanoNutricionalAtivoTrue();
    List<Aluno> findByPlanoNutricionalAtivoFalse();

    // Buscar por status geral do aluno
    List<Aluno> findByStatusAtivoTrue();
    List<Aluno> findByStatusAtivoFalse();

    // Buscar por email do usuário vinculado
    Optional<Aluno> findByUsuarioEmail(String email);

    // Buscar por ID do usuário vinculado
    Optional<Aluno> findByUsuarioId(Long usuarioId);

    // Verificar se existe aluno vinculado ao usuário
    boolean existsByUsuarioId(Long usuarioId);
    boolean existsByUsuarioEmail(String email);

    // Buscar por nutricionista
    List<Aluno> findByNutricionistaId(Long nutricionistaId);

    // Buscar por professor
    List<Aluno> findByProfessorId(Long professorId);

    // Buscar por objetivo
    List<Aluno> findByObjetivo(String objetivo);
    List<Aluno> findByObjetivoContainingIgnoreCase(String objetivo);

    // Buscar por faixa etária
    List<Aluno> findByIdadeBetween(Integer idadeMin, Integer idadeMax);
    List<Aluno> findByIdadeGreaterThanEqual(Integer idade);
    List<Aluno> findByIdadeLessThanEqual(Integer idade);

    // Buscar por faixa de peso
    @Query("SELECT a FROM Aluno a WHERE a.peso BETWEEN :pesoMin AND :pesoMax")
    List<Aluno> findByPesoBetween(@Param("pesoMin") Double pesoMin, @Param("pesoMax") Double pesoMax);

    // Buscar por faixa de altura
    @Query("SELECT a FROM Aluno a WHERE a.altura BETWEEN :alturaMin AND :alturaMax")
    List<Aluno> findByAlturaBetween(@Param("alturaMin") Double alturaMin, @Param("alturaMax") Double alturaMax);

    // Buscar por matrícula
    Optional<Aluno> findByMatricula(String matricula);

    // Buscar alunos sem nutricionista vinculado
    @Query("SELECT a FROM Aluno a WHERE a.nutricionista IS NULL AND a.statusAtivo = true")
    List<Aluno> findAlunosSemNutricionista();

    // Buscar alunos sem professor vinculado
    @Query("SELECT a FROM Aluno a WHERE a.professor IS NULL AND a.statusAtivo = true")
    List<Aluno> findAlunosSemProfessor();

    // Buscar alunos com plano nutricional ativo e ativos
    @Query("SELECT a FROM Aluno a WHERE a.planoNutricionalAtivo = true AND a.statusAtivo = true")
    List<Aluno> findAlunosAtivosComPlanoNutricional();

    // Buscar alunos inativos
    @Query("SELECT a FROM Aluno a WHERE a.statusAtivo = false")
    List<Aluno> findAlunosInativos();

    // Contar alunos por status
    long countByStatusAtivoTrue();
    long countByStatusAtivoFalse();
    long countByPlanoNutricionalAtivoTrue();
    long countByPlanoNutricionalAtivoFalse();

    @Query("SELECT COUNT(a) FROM Aluno a WHERE a.nutricionista.id = :nutricionistaId")
    long countByNutricionistaId(@Param("nutricionistaId") Long nutricionistaId);

    @Query("SELECT COUNT(a) FROM Aluno a WHERE a.professor.id = :professorId")
    long countByProfessorId(@Param("professorId") Long professorId);

    // Buscar alunos por nome (através do usuário)
    @Query("SELECT a FROM Aluno a WHERE LOWER(a.usuario.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Aluno> findByUsuarioNomeContainingIgnoreCase(@Param("nome") String nome);

    // Buscar alunos ordenados por data de criação (se tiver no usuário)
    @Query("SELECT a FROM Aluno a ORDER BY a.usuario.dataCriacao DESC")
    List<Aluno> findAllOrderByDataCriacaoDesc();

    // Buscar alunos com filtros combinados
    @Query("SELECT a FROM Aluno a WHERE " +
            "(:statusAtivo IS NULL OR a.statusAtivo = :statusAtivo) AND " +
            "(:planoNutricionalAtivo IS NULL OR a.planoNutricionalAtivo = :planoNutricionalAtivo) AND " +
            "(:nutricionistaId IS NULL OR a.nutricionista.id = :nutricionistaId) AND " +
            "(:professorId IS NULL OR a.professor.id = :professorId)")
    List<Aluno> findWithFilters(@Param("statusAtivo") Boolean statusAtivo, @Param("planoNutricionalAtivo") Boolean planoNutricionalAtivo, @Param("nutricionistaId") Long nutricionistaId, @Param("professorId") Long professorId);

    // Buscar top 10 alunos mais recentes
    @Query(value = "SELECT * FROM alunos ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Aluno> findTop10ByOrderByIdDesc();

    // Buscar alunos com data de criação recente
    @Query("SELECT a FROM Aluno a WHERE a.usuario.dataCriacao >= :data")
    List<Aluno> findByDataCriacaoAfter(@Param("data") java.time.LocalDateTime data);

}