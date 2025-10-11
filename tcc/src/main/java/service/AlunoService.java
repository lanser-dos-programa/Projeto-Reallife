package service;

import entity.Aluno;
import entity.Exercicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.AlunoRepository;

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

    public void deletaraluno(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado para exclusão.");
        }
        alunoRepository.deleteById(id);
    }

}
