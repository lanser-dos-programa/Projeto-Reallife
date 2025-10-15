package service;

import entity.Aluno;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.AlunoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    // Cadastrar aluno
    public Aluno cadastrarAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    // Listar todos
    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    // Buscar por ID
    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
    }

    // Atualizar dados
    public Aluno atualizarAluno(Long id, Aluno dadosAtualizados) {
        Aluno existente = buscarPorId(id);
        existente.setNome(dadosAtualizados.getNome());
        existente.setEmail(dadosAtualizados.getEmail());
        existente.setTelefone(dadosAtualizados.getTelefone());
        existente.setAtivo(dadosAtualizados.isAtivo());
        return alunoRepository.save(existente);
    }

    // Deletar
    public void deletarAluno(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado para exclusão.");
        }
        alunoRepository.deleteById(id);
    }

    // Ativar plano nutricional
    public Aluno ativarPlanoNutricional(Long id) {
        Aluno aluno = buscarPorId(id);
        if (!aluno.isAtivo()) {
            throw new RuntimeException("Não é possível ativar plano nutricional para aluno inativo.");
        }
        aluno.setPlanoNutricionalAtivo(true);
        return alunoRepository.save(aluno);
    }

    // Desativar plano nutricional
    public Aluno desativarPlanoNutricional(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setPlanoNutricionalAtivo(false);
        return alunoRepository.save(aluno);
    }
}
