package com.reallife.tcc.service;

import com.reallife.tcc.dto.RegistroDto;
import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.repository.UsuarioRepository;
import com.reallife.tcc.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // LOGIN
    public String login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        return "Login realizado com sucesso!";
    }

    // REGISTRO
    public void registrar(RegistroDto dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .cpf(dto.getCpf())
                .role(Role.valueOf(dto.getRole().toUpperCase()))
                .ativo(true)
                .build();

        usuarioRepository.save(usuario);
    }
}
