package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Optional<Professor> findByUsuarioEmail(String email);
    Optional<Professor> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioId(Long usuarioId);
    boolean existsByUsuarioEmail(String email);

    List<Professor> findByAtivoTrue();
    List<Professor> findByAtivoFalse();

    List<Professor> findByEspecialidade(String especialidade);
    List<Professor> findByEspecialidadeContainingIgnoreCase(String especialidade);

    List<Professor> findByFormacaoContaining(String formacao);

    @Query("SELECT p FROM Professor p WHERE LOWER(p.usuario.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Professor> findByUsuarioNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT p FROM Professor p WHERE p.anosExperiencia >= :anosExperiencia")
    List<Professor> findByAnosExperienciaGreaterThanEqual(@Param("anosExperiencia") Integer anosExperiencia);

    long countByAtivoTrue();
    long countByAtivoFalse();

    @Query("SELECT p FROM Professor p ORDER BY p.usuario.dataCriacao DESC")
    List<Professor> findAllOrderByDataCriacaoDesc();

    @Query("SELECT COUNT(a) FROM Aluno a WHERE a.professor.id = :professorId")
    long countAlunosByProfessorId(@Param("professorId") Long professorId);

    @Query(value = "SELECT * FROM professores WHERE ativo = true ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Professor> findTop10Ativos();
}