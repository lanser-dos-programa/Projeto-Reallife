package com.reallife.tcc.service;

import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.repository.UsuarioRepository;
import com.reallife.tcc.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // LISTAR TODOS
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // BUSCAR POR ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    // CRIAR
    public Usuario criar(Usuario usuario) {

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já está cadastrado!");
        }

        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new RuntimeException("CPF já está cadastrado!");
        }

        return usuarioRepository.save(usuario);
    }

    // ATUALIZAR
    public Usuario atualizar(Long id, Usuario dados) {
        Usuario usuario = buscarPorId(id);

        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(dados.getSenha());
        usuario.setCpf(dados.getCpf());

        return usuarioRepository.save(usuario);
    }

    // ALTERAR SENHA
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarPorId(id);

        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new RuntimeException("Senha atual incorreta.");
        }

        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
    }

    // ALTERAR ROLE
    public Usuario DefinirRole(Long id, Role role) {
        Usuario usuario = buscarPorId(id);

        usuario.setRole(role);
        return usuarioRepository.save(usuario);
    }

    // ATIVAR / DESATIVAR USUÁRIO
    public Usuario alterarStatus(Long id, boolean ativo) {
        Usuario usuario = buscarPorId(id);

        usuario.setAtivo(ativo);
        return usuarioRepository.save(usuario);
    }

    // DELETAR
    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }

    // BUSCAR POR EMAIL
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado."));
    }

    public Usuario definirRole(Long id, Role role) {
        Usuario usuario = buscarPorId(id);
        usuario.setRole(role);
        return usuarioRepository.save(usuario);
    }
}
