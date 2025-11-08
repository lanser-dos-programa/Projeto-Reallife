package com.reallife.tcc.service;

import com.reallife.tcc.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.reallife.tcc.repository.UsuarioRepository;
import com.reallife.tcc.security.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Registro padrão de usuário
    public Usuario registrarUsuario(Usuario usuario) {
        // Verifica se já existe usuário com o mesmo email
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Usuário com esse email já existe");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRole(Role.ROLE_ALUNO); // role padrão
        usuario.setAtivo(true);
        return usuarioRepository.save(usuario);
    }

    // Definir nova role
    public Usuario definirRole(Long id, Role novoRole) {
        Usuario usuario = buscarPorId(id);
        usuario.setRole(novoRole);
        return usuarioRepository.save(usuario);
    }

    // Listar todos os usuários
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Autenticar usuário pelo email e senha
    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        return usuario;
    }
}
