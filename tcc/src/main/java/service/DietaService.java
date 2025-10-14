package service;

import entity.Dietas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.DietasRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DietaService {
    private final DietasRepository dietasRepository;

    public Dietas cadastrarDietas(Dietas dietas) {
        return dietasRepository.save(dietas);
    }


    public List<Dietas> listarFicha() {
        return dietasRepository.findAll();
    }

    public Dietas buscarPorId(Long id) {
        return dietasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada não encontrada com o ID: " + id));
    }

    public void deletardieta(Long id) {
        if (!dietasRepository.existsById(id)) {
            throw new RuntimeException("Dieta não encontrada para exclusão.");
        }
        dietasRepository.deleteById(id);
    }
}