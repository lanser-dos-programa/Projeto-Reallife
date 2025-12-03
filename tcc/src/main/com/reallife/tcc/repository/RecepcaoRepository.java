package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Recepcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecepcaoRepository extends JpaRepository<Recepcao, Long> {

    // Buscar por email
    Optional<Recepcao> findByEmail(String email);

    // Buscar por usuário ID
    Optional<Recepcao> findByUsuarioId(Long usuarioId);

    // Buscar por nome (contém)
    List<Recepcao> findByNomeContainingIgnoreCase(String nome);

    // Buscar por telefone
    List<Recepcao> findByTelefone(String telefone);

    // Buscar ativos
    List<Recepcao> findByAtivoTrue();

    // Buscar inativos
    List<Recepcao> findByAtivoFalse();

    // Verificar se email existe
    boolean existsByEmail(String email);

    // Verificar se usuário existe
    boolean existsByUsuarioId(Long usuarioId);

    // Contar total
    long count();

    // Contar ativos
    long countByAtivoTrue();

    // Contar inativos
    long countByAtivoFalse();

    // Buscar recepcionistas recentes (últimos 10)
    @Query(value = "SELECT * FROM recepcao ORDER BY data_cadastro DESC LIMIT 10", nativeQuery = true)
    List<Recepcao> findTop10Recent();

    // Buscar por período
    @Query("SELECT r FROM Recepcao r WHERE r.dataCadastro BETWEEN :inicio AND :fim")
    List<Recepcao> findByPeriodo(@Param("inicio") java.time.LocalDateTime inicio,
                                 @Param("fim") java.time.LocalDateTime fim);
}