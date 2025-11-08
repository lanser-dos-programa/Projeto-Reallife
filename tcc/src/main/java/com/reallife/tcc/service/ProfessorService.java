package com.reallife.tcc.service;

import com.reallife.tcc.entity.Professor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.reallife.tcc.repository.ProfessorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public Professor cadastrarProfessor(Professor professor){
        return professorRepository.save(professor);
    }
    public List<Professor> listarProfessor() {
        return professorRepository.findAll();
    }
    public Professor buscarPorId(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + id));
    }

    public void deletarProfessor(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new RuntimeException("Professor não encontrado para exclusão.");
        }
        professorRepository.deleteById(id);
    }
}
