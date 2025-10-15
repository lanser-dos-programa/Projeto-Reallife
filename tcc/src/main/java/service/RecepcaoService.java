package service;

import entity.Recepcao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.RecepcaoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecepcaoService {

    private final RecepcaoRepository recepcaoRepository;

    // Cadastrar novo funcionário da recepção
    public Recepcao cadastrarRecepcionista(Recepcao recepcao) {
        if (recepcaoRepository.existsByEmail(recepcao.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado para outro funcionário da recepção.");
        }

        // Aqui tu pode adicionar uma criptografia de senha antes de salvar
        // tipo: recepcao.setSenha(passwordEncoder.encode(recepcao.getSenha()));

        return recepcaoRepository.save(recepcao);
    }

    // Listar todos os funcionários da recepção
    public List<Recepcao> listarTodos() {
        return recepcaoRepository.findAll();
    }

    // Buscar por ID
    public Recepcao buscarPorId(Long id) {
        return recepcaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário da recepção não encontrado."));
    }

    // Atualizar dados de um funcionário
    public Recepcao atualizar(Long id, Recepcao dadosAtualizados) {
        Recepcao existente = buscarPorId(id);
        existente.setNome(dadosAtualizados.getNome());
        existente.setEmail(dadosAtualizados.getEmail());
        existente.setTelefone(dadosAtualizados.getTelefone());
        existente.setAtivo(dadosAtualizados.isAtivo());
        return recepcaoRepository.save(existente);
    }

    // Deletar funcionário
    public void deletar(Long id) {
        recepcaoRepository.deleteById(id);
    }

    // Login básico (pode ser substituído por Spring Security depois)
    public Recepcao login(String email, String senha) {
        Optional<Recepcao> recepcao = recepcaoRepository.findByEmail(email);
        if (recepcao.isEmpty() || !recepcao.get().getSenha().equals(senha)) {
            throw new RuntimeException("E-mail ou senha inválidos.");
        }
        return recepcao.get();
    }
}
