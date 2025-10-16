package service;

import entity.Exercicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.ExercicioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExercicioService {

    private final ExercicioRepository exercicioRepository;

    public Exercicio cadastrarExercicio(Exercicio exercicio) {
        return exercicioRepository.save(exercicio);
    }

    public List<Exercicio> listarExercicios() {
        return exercicioRepository.findAll();
    }

    public Exercicio buscarPorId(Long id) {
        return exercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado com id: " + id));
    }

    public void deletarExercicio(Long id) {
        if (!exercicioRepository.existsById(id)) {
            throw new RuntimeException("Exercício não encontrado para exclusão.");
        }
        exercicioRepository.deleteById(id);
    }
}
