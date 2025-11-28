package com.reallife.tcc.controller;

import com.reallife.tcc.dto.RegisterRequest;
import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.repository.UsuarioRepository;
import com.reallife.tcc.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            System.out.println("Recebendo registro: " + request.getEmail());
            System.out.println("Dados recebidos - Nome: " + request.getNome() +
                    ", Email: " + request.getEmail() +
                    ", CPF: " + request.getCpf() +
                    ", Role: " + request.getRole());

            // Verifica se email já existe
            if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
                System.out.println("Email já existe: " + request.getEmail());
                return ResponseEntity.badRequest().body("Email já cadastrado");
            } else {
                System.out.println("Email disponível: " + request.getEmail());
            }

            // Verifica se CPF já existe
            if (usuarioRepository.findByCpf(request.getCpf()).isPresent()) {
                System.out.println("CPF já existe: " + request.getCpf());
                return ResponseEntity.badRequest().body("CPF já cadastrado");
            } else {
                System.out.println("CPF disponível: " + request.getCpf());
            }

            // Valida o role
            Role role;
            try {
                role = Role.valueOf(request.getRole().toUpperCase());
                System.out.println("Role válido: " + role);
            } catch (IllegalArgumentException e) {
                System.out.println("Role inválido: " + request.getRole());
                return ResponseEntity.badRequest().body("Role inválido. Use: ALUNO, NUTRICIONISTA ou PROFESSOR");
            }

            // Cria novo usuário
            Usuario novoUsuario = Usuario.builder()
                    .nome(request.getNome())
                    .email(request.getEmail())
                    .senha(passwordEncoder.encode(request.getSenha()))
                    .cpf(request.getCpf())
                    .role(role)
                    .ativo(true)
                    .dataCriacao(LocalDateTime.now())
                    .dataAtualizacao(LocalDateTime.now())
                    .build();

            System.out.println("👤 Usuário criado - Senha criptografada: " + novoUsuario.getSenha());

            Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
            System.out.println("Usuário salvo no banco com ID: " + usuarioSalvo.getId());

            return ResponseEntity.ok("Usuário " + role + " registrado com sucesso! ID: " + usuarioSalvo.getId());

        } catch (Exception e) {
            System.out.println("ERRO GERAL no registro: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro no registro: " + e.getMessage());
        }
    }
}