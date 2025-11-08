package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Usuario;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@SpringBootApplication
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<Usuario>findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}