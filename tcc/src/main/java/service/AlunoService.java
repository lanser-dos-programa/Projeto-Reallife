package service;

import entity.Aluno;
import entity.Exercicio;
import entity.Recepcao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.AlunoRepository;
import repository.RecepcaoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    // Cadastro do Aluno
    public Aluno cadastrarAluno(Aluno aluno){
        return alunoRepository.save(aluno);
    }
    // Procura do ALuno
    public List<Aluno> listarAlunos(){
        return alunoRepository.findAll();
        
    }
    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
    }

    // Atualizar dados de um funcionário
    public Recepcao atualizar(Long id, Recepcao dadosAtualizados) {
        Aluno existente = buscarPorId(id);
        existente.setNome(dadosAtualizados.getNome());
        existente.setEmail(dadosAtualizados.getEmail());
        existente.setTelefone(dadosAtualizados.getTelefone());
        existente.setAtivo(dadosAtualizados.isAtivo());
        return RecepcaoRepository.save(existente);
    }

    public void deletaraluno(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado para exclusão.");
        }
        alunoRepository.deleteById(id);
    }

}
