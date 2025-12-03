package com.reallife.tcc.service;

import com.reallife.tcc.entity.Recepcao;
import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.repository.RecepcaoRepository;
import com.reallife.tcc.repository.UsuarioRepository;
import com.reallife.tcc.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecepcaoService {

    private final RecepcaoRepository recepcaoRepository;
    private final UsuarioRepository usuarioRepository;

    // ========== CRUD BÁSICO ==========

    public Recepcao cadastrarRecepcionista(Recepcao recepcao) {
        // Verificar se email já existe
        if (recepcaoRepository.existsByEmail(recepcao.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Definir data de cadastro
        recepcao.setDataCadastro(LocalDateTime.now());
        recepcao.setAtivo(true);

        // Se tiver usuário vinculado, verificar se existe
        if (recepcao.getUsuario() != null && recepcao.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(recepcao.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Verificar se usuário já está vinculado a outro recepcionista
            if (recepcaoRepository.existsByUsuarioId(usuario.getId())) {
                throw new RuntimeException("Usuário já vinculado a outro recepcionista");
            }

            // Definir role como RECEPCIONISTA
            usuario.setRole(Role.ROLE_RECEPCAO);
            usuarioRepository.save(usuario);
        }

        return recepcaoRepository.save(recepcao);
    }

    public List<Recepcao> listarTodos() {
        return recepcaoRepository.findAll();
    }

    public Recepcao buscarPorId(Long id) {
        return recepcaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recepcionista não encontrado com id: " + id));
    }

    public Recepcao atualizar(Long id, Recepcao dadosAtualizados) {
        Recepcao existente = buscarPorId(id);

        // Atualizar campos permitidos
        if (dadosAtualizados.getNome() != null) {
            existente.setNome(dadosAtualizados.getNome());
        }
        if (dadosAtualizados.getEmail() != null) {
            // Verificar se novo email já existe (e não é o mesmo)
            if (!existente.getEmail().equals(dadosAtualizados.getEmail()) &&
                    recepcaoRepository.existsByEmail(dadosAtualizados.getEmail())) {
                throw new RuntimeException("Email já cadastrado para outro recepcionista");
            }
            existente.setEmail(dadosAtualizados.getEmail());
        }
        if (dadosAtualizados.getTelefone() != null) {
            existente.setTelefone(dadosAtualizados.getTelefone());
        }
        if (dadosAtualizados.getCpf() != null) {
            existente.setCpf(dadosAtualizados.getCpf());
        }
        if (dadosAtualizados.getEndereco() != null) {
            existente.setEndereco(dadosAtualizados.getEndereco());
        }
        if (dadosAtualizados.getDataNascimento() != null) {
            existente.setDataNascimento(dadosAtualizados.getDataNascimento());
        }

        return recepcaoRepository.save(existente);
    }

    public void deletar(Long id) {
        Recepcao recepcionista = buscarPorId(id);

        // Desvincular usuário se existir
        if (recepcionista.getUsuario() != null) {
            Usuario usuario = recepcionista.getUsuario();
            usuario.setRole(null); // Remover role de recepcionista
            usuarioRepository.save(usuario);
        }

        recepcaoRepository.delete(recepcionista);
    }

    // ========== LOGIN ==========

    public Recepcao login(String email, String senha) {
        Recepcao recepcionista = recepcaoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Recepcionista não encontrado"));

        // Verificar se está ativo
        if (!recepcionista.isAtivo()) {
            throw new RuntimeException("Recepcionista inativo");
        }

        // Verificar senha (simplificado - idealmente usar BCrypt)
        if (recepcionista.getUsuario() != null) {
            // Usar autenticação via Usuario
            if (!recepcionista.getUsuario().getSenha().equals(senha)) {
                throw new RuntimeException("Senha incorreta");
            }
        } else {
            // Senha direta no recepcionista (modo legado)
            if (!recepcionista.getSenha().equals(senha)) {
                throw new RuntimeException("Senha incorreta");
            }
        }

        return recepcionista;
    }

    // ========== MÉTODOS ADICIONAIS ==========

    public Recepcao buscarPorUsuarioId(Long usuarioId) {
        return recepcaoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Recepcionista não encontrado para o usuário"));
    }

    public Recepcao buscarPorEmail(String email) {
        return recepcaoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Recepcionista não encontrado"));
    }

    public List<Recepcao> buscarPorNome(String nome) {
        return recepcaoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Recepcao> listarAtivos() {
        return recepcaoRepository.findByAtivoTrue();
    }

    public List<Recepcao> listarInativos() {
        return recepcaoRepository.findByAtivoFalse();
    }

    public Recepcao ativarRecepcionista(Long id) {
        Recepcao recepcionista = buscarPorId(id);
        recepcionista.setAtivo(true);

        // Ativar usuário vinculado também
        if (recepcionista.getUsuario() != null) {
            recepcionista.getUsuario().setAtivo(true);
            usuarioRepository.save(recepcionista.getUsuario());
        }

        return recepcaoRepository.save(recepcionista);
    }

    public Recepcao desativarRecepcionista(Long id) {
        Recepcao recepcionista = buscarPorId(id);
        recepcionista.setAtivo(false);

        // Desativar usuário vinculado também
        if (recepcionista.getUsuario() != null) {
            recepcionista.getUsuario().setAtivo(false);
            usuarioRepository.save(recepcionista.getUsuario());
        }

        return recepcaoRepository.save(recepcionista);
    }

    public Recepcao vincularUsuario(Long recepcionistaId, Long usuarioId) {
        Recepcao recepcionista = buscarPorId(recepcionistaId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verificar se usuário já está vinculado
        if (recepcaoRepository.existsByUsuarioId(usuarioId)) {
            throw new RuntimeException("Usuário já vinculado a outro recepcionista");
        }

        // Definir role como RECEPCIONISTA
        usuario.setRole(Role.ROLE_RECEPCAO);
        usuarioRepository.save(usuario);

        recepcionista.setUsuario(usuario);
        return recepcaoRepository.save(recepcionista);
    }

    public Recepcao desvincularUsuario(Long recepcionistaId) {
        Recepcao recepcionista = buscarPorId(recepcionistaId);

        if (recepcionista.getUsuario() != null) {
            Usuario usuario = recepcionista.getUsuario();
            usuario.setRole(null); // Remover role
            usuarioRepository.save(usuario);

            recepcionista.setUsuario(null);
        }

        return recepcaoRepository.save(recepcionista);
    }

    // ========== VALIDAÇÕES ==========

    public boolean existePorEmail(String email) {
        return recepcaoRepository.existsByEmail(email);
    }

    public boolean existePorUsuarioId(Long usuarioId) {
        return recepcaoRepository.existsByUsuarioId(usuarioId);
    }

    public boolean isAtivo(Long id) {
        Recepcao recepcionista = buscarPorId(id);
        return recepcionista.isAtivo();
    }

    // ========== ESTATÍSTICAS ==========

    public long contarTotalRecepcionistas() {
        return recepcaoRepository.count();
    }

    public long contarRecepcionistasAtivos() {
        return recepcaoRepository.countByAtivoTrue();
    }

    public long contarRecepcionistasInativos() {
        return recepcaoRepository.countByAtivoFalse();
    }

    public List<Recepcao> listarRecentes() {
        return recepcaoRepository.findTop10Recent();
    }

    // ========== BUSCA COM FILTROS ==========

    public List<Recepcao> buscarComFiltros(String nome, String email, Boolean ativo) {
        List<Recepcao> todos = recepcaoRepository.findAll();

        return todos.stream()
                .filter(r -> nome == null ||
                        (r.getNome() != null && r.getNome().toLowerCase().contains(nome.toLowerCase())))
                .filter(r -> email == null ||
                        (r.getEmail() != null && r.getEmail().equalsIgnoreCase(email)))
                .filter(r -> ativo == null || r.isAtivo() == ativo)
                .toList();
    }
}