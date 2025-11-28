package com.reallife.tcc.service;

import com.reallife.tcc.dto.FichaDto;
import com.reallife.tcc.entity.Aluno;
import com.reallife.tcc.entity.Exercicio;
import com.reallife.tcc.entity.FichaDeTreino;
import com.reallife.tcc.repository.AlunoRepository;
import com.reallife.tcc.repository.FichaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FichaService {

    private final FichaRepository fichaRepository;
    private final ExercicioService exercicioService;
    private final AlunoRepository alunoRepository;

    public FichaDeTreino criarFicha(FichaDto dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Set<Exercicio> exercicios = dto.getExerciciosIds()
                .stream()
                .map(exercicioService::buscarPorId)
                .collect(Collectors.toSet());

        FichaDeTreino ficha = new FichaDeTreino();
        ficha.setNome(dto.getNome());
        ficha.setObjetivo(dto.getObjetivo());
        ficha.setAluno(aluno);
        ficha.setExercicios(exercicios);

        return fichaRepository.save(ficha);
    }
}
