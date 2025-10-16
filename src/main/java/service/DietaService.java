package service;

import entity.Aluno;
import entity.Dieta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.DietaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DietaService {

    private final DietaRepository dietaRepository;

    // Cadastrar dieta (padrão)
    public Dieta cadastrarDieta(Dieta dieta) {
        return dietaRepository.save(dieta);
    }

    // Listar todas as dietas
    public List<Dieta> listarDietas() {
        return dietaRepository.findAll();
    }

    // Buscar dieta por ID
    public Dieta buscarPorId(Long id) {
        return dietaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada com o ID: " + id));
    }

    // Deletar dieta por ID
    public void deletarDieta(Long id) {
        if (!dietaRepository.existsById(id)) {
            throw new RuntimeException("Dieta não encontrada para exclusão.");
        }
        dietaRepository.deleteById(id);
    }

    // Salvar ou atualizar dieta de um aluno
    public Dieta salvarOuAtualizarDieta(Aluno aluno, Dieta dieta) {
        Dieta existente = dietaRepository.findByAlunoId(aluno.getId());

        if (existente != null) {
            // Atualiza a existente
            existente.setTipo(dieta.getTipo());
            existente.setRefeicoes(dieta.getRefeicoes());
            existente.setKcal(dieta.getKcal());
            existente.setObjetivo(dieta.getObjetivo());
            return dietaRepository.save(existente);
        } else {
            // Cria nova e associa ao aluno
            dieta.setAluno(aluno);
            return dietaRepository.save(dieta);
        }
    }

    // Buscar dieta por ID do aluno
    public Dieta buscarPorAluno(Long alunoId) {
        Dieta dieta = dietaRepository.findByAlunoId(alunoId);
        if (dieta == null) {
            throw new RuntimeException("Nenhuma dieta encontrada para o aluno com ID: " + alunoId);
        }
        return dieta;
    }

    // Deletar dieta associada a um aluno
    public void deletarDietaPorAluno(Long alunoId) {
        Dieta dieta = dietaRepository.findByAlunoId(alunoId);
        if (dieta != null) {
            dietaRepository.delete(dieta);
        }
    }
}
