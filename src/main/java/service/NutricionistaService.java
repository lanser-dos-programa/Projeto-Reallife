package service;

import entity.Nutricionista;
import entity.Professor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.NutricionistaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NutricionistaService {

    private final NutricionistaRepository nutricionistaRepository;

    public Nutricionista cadastrarNutricionista(Nutricionista nutricionista){
        return nutricionistaRepository.save(nutricionista);
    }
    public List<Nutricionista> listarNutricionista() {
        return nutricionistaRepository.findAll();
    }
    public Nutricionista buscarPorId(Long id) {
        return nutricionistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com id: " + id));
    }

    public void deletarProfessor(Long id) {
        if (!nutricionistaRepository.existsById(id)) {
            throw new RuntimeException("Nutricionista não encontrado para exclusão.");
        }
        nutricionistaRepository.deleteById(id);
    }
}
