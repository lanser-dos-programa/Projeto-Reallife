package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    List<Usuario> findByRole(Role role);
    List<Usuario> findByAtivoTrue();
    List<Usuario> findByAtivoFalse();

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT u FROM Usuario u WHERE u.role = :role AND u.ativo = true")
    List<Usuario> findAtivosByRole(@Param("role") Role role);

    long countByRole(Role role);
    long countByAtivoTrue();
    long countByAtivoFalse();

    @Query("SELECT u FROM Usuario u ORDER BY u.dataCriacao DESC")
    List<Usuario> findAllOrderByDataCriacaoDesc();
}