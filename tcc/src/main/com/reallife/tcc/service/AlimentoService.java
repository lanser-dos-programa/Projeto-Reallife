package com.reallife.tcc.service;

import com.reallife.tcc.entity.Alimentos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.reallife.tcc.repository.AlimentoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;

    public Alimentos cadastrarAlimento(Alimentos alimentos) {
        return alimentoRepository.save(alimentos);
    }

    public List<Alimentos> listarALimentos() {
        return alimentoRepository.findAll();
    }

    public Alimentos buscarPorId(Long id) {
        return alimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alimento não encontrado com id: " + id));
    }

    public void deletarExercicio(Long id) {
        if (!alimentoRepository.existsById(id)) {
            throw new RuntimeException("Alimento não encontrado para exclusão.");
        }
        alimentoRepository.deleteById(id);
    }
}
