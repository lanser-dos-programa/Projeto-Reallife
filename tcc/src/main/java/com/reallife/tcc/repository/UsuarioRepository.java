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

    // Buscar por email
    Optional<Usuario> findByEmail(String email);

    // Buscar por CPF
    Optional<Usuario> findByCpf(String cpf);

    // Verificar existência por email
    boolean existsByEmail(String email);

    // Verificar existência por CPF
    boolean existsByCpf(String cpf);

    // Buscar usuários por role
    List<Usuario> findByRole(Role role);

    // Buscar usuários ativos
    List<Usuario> findByAtivoTrue();

    // Buscar usuários inativos
    List<Usuario> findByAtivoFalse();

    // Buscar usuários ativos por role
    List<Usuario> findByAtivoTrueAndRole(Role role);

    // Buscar por nome (case insensitive)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    // Buscar por parte do email
    List<Usuario> findByEmailContaining(String email);

    // Buscar usuários criados após uma data
    List<Usuario> findByDataCriacaoAfter(java.time.LocalDateTime data);

    // Buscar usuários criados antes de uma data
    List<Usuario> findByDataCriacaoBefore(java.time.LocalDateTime data);

    // Query customizada - contar usuários por role
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.role = :role")
    long countByRole(@Param("role") Role role);

    // Query customizada - buscar usuários com email de determinado domínio
    @Query("SELECT u FROM Usuario u WHERE u.email LIKE %:dominio%")
    List<Usuario> findByEmailDomain(@Param("dominio") String dominio);

    // Query customizada - buscar usuários ordenados por data de criação
    @Query("SELECT u FROM Usuario u ORDER BY u.dataCriacao DESC")
    List<Usuario> findAllOrderByDataCriacaoDesc();

    // Query customizada - buscar usuários por múltiplos roles
    @Query("SELECT u FROM Usuario u WHERE u.role IN :roles")
    List<Usuario> findByRoles(@Param("roles") List<Role> roles);

    // Verificar se existe usuário com email e que está ativo
    boolean existsByEmailAndAtivoTrue(String email);

    // Buscar usuário por email e senha (útil para login customizado)
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
    Optional<Usuario> findByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
}