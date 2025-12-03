package com.reallife.tcc.service;

import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.repository.UsuarioRepository;
import com.reallife.tcc.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // --- ADICIONA ESSE MÉTODO AQUI ---
    public List<Usuario> buscarUsuariosComFiltros(String nome, String email, Role role) {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> nome == null ||
                        usuario.getNome().toLowerCase().contains(nome.toLowerCase()))
                .filter(usuario -> email == null ||
                        usuario.getEmail().toLowerCase().contains(email.toLowerCase()))
                .filter(usuario -> role == null ||
                        usuario.getRole() == role)
                .collect(Collectors.toList());
    }
    // --- FIM DO MÉTODO ADICIONADO ---

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public Usuario criar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já está cadastrado!");
        }
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new RuntimeException("CPF já está cadastrado!");
        }
        usuario.setAtivo(true);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dados) {
        Usuario usuario = buscarPorId(id);
        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(dados.getSenha());
        usuario.setCpf(dados.getCpf());
        return usuarioRepository.save(usuario);
    }

    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarPorId(id);
        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new RuntimeException("Senha atual incorreta.");
        }
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
    }

    public Usuario definirRole(Long id, Role role) {
        Usuario usuario = buscarPorId(id);
        usuario.setRole(role);
        return usuarioRepository.save(usuario);
    }

    public Usuario alterarStatus(Long id, boolean ativo) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(ativo);
        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado."));
    }

    public void redefinirSenha(Long id, String novaSenha) {
        Usuario usuario = buscarPorId(id);
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
    }

    public List<Usuario> buscarPorRole(Role role) {
        return usuarioRepository.findByRole(role);
    }

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Usuario> buscarAtivos() {
        return usuarioRepository.findByAtivo(true);
    }

    public List<Usuario> buscarInativos() {
        return usuarioRepository.findByAtivo(false);
    }

    public Map<String, Long> contarUsuariosPorRole() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .collect(Collectors.groupingBy(
                        u -> u.getRole().name(),
                        Collectors.counting()
                ));
    }

    public long contarTotalUsuarios() {
        return usuarioRepository.count();
    }
}