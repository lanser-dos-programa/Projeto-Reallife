package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
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
    List<Usuario> findByAtivo(boolean ativo);
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
}