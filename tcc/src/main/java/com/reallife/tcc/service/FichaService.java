package com.reallife.tcc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.reallife.tcc.entity.FichaDeTreino;
import com.reallife.tcc.repository.FichaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FichaService {
    private final FichaRepository fichaRepository;

    public FichaDeTreino cadastrarFicha(FichaDeTreino fichaDeTreino) {
        return fichaRepository.save(fichaDeTreino);
    }

    public List<FichaDeTreino> listarFicha() {
        return fichaRepository.findAll();
    }

    public FichaDeTreino buscarPorId(Long id) {
        return fichaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada com o ID: " + id));
    }

    public void deletarFicha(Long id) {
        if (!fichaRepository.existsById(id)) {
            throw new RuntimeException("Ficha não encontrada para exclusão.");
        }
        fichaRepository.deleteById(id);
    }
}