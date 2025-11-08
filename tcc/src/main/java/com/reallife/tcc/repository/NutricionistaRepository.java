package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Nutricionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long> {

    Optional<Nutricionista> findByUsuarioEmail(String email);
    Optional<Nutricionista> findByUsuarioId(Long usuarioId);
    Optional<Nutricionista> findByRegistroProfissional(String registroProfissional);

    boolean existsByUsuarioId(Long usuarioId);
    boolean existsByUsuarioEmail(String email);
    boolean existsByRegistroProfissional(String registroProfissional);

    List<Nutricionista> findByAtivoTrue();
    List<Nutricionista> findByAtivoFalse();

    List<Nutricionista> findByEspecialidade(String especialidade);
    List<Nutricionista> findByEspecialidadeContainingIgnoreCase(String especialidade);

    @Query("SELECT n FROM Nutricionista n WHERE LOWER(n.usuario.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Nutricionista> findByUsuarioNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT n FROM Nutricionista n WHERE n.formacao LIKE %:formacao%")
    List<Nutricionista> findByFormacaoContaining(@Param("formacao") String formacao);

    long countByAtivoTrue();
    long countByAtivoFalse();

    @Query("SELECT n FROM Nutricionista n ORDER BY n.usuario.dataCriacao DESC")
    List<Nutricionista> findAllOrderByDataCriacaoDesc();

    @Query("SELECT COUNT(a) FROM Aluno a WHERE a.nutricionista.id = :nutricionistaId")
    long countAlunosByNutricionistaId(@Param("nutricionistaId") Long nutricionistaId);

    @Query(value = "SELECT * FROM nutricionistas WHERE ativo = true ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Nutricionista> findTop10Ativos();
}