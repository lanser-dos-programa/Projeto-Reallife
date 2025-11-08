package com.reallife.tcc.service;

import com.reallife.tcc.entity.Dieta;
import com.reallife.tcc.entity.Aluno;
import com.reallife.tcc.entity.Nutricionista;
import com.reallife.tcc.repository.DietaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DietaService {

    private final DietaRepository dietaRepository;
    private final AlunoService alunoService;
    private final NutricionistaService nutricionistaService;

    // CADASTRO E ATUALIZAÇÃO
    public Dieta salvarOuAtualizarDieta(Long alunoId, Dieta dieta) {
        Aluno aluno = alunoService.buscarEntidadePorId(alunoId);

        // Verifica se já existe dieta para este aluno
        Optional<Dieta> dietaExistente = dietaRepository.findByAlunoId(alunoId);

        if (dietaExistente.isPresent()) {
            // Atualiza dieta existente
            Dieta existente = dietaExistente.get();
            existente.setNome(dieta.getNome());
            existente.setDescricao(dieta.getDescricao());
            existente.setDataInicio(dieta.getDataInicio());
            existente.setDataFim(dieta.getDataFim());
            existente.setObjetivo(dieta.getObjetivo());
            existente.setAlimentos(dieta.getAlimentos());
            return dietaRepository.save(existente);
        } else {
            // Cria nova dieta
            dieta.setAluno(aluno);
            if (aluno.getNutricionista() != null) {
                dieta.setNutricionista(aluno.getNutricionista());
            }
            return dietaRepository.save(dieta);
        }
    }

    public Dieta criarDieta(Dieta dieta, Long alunoId, Long nutricionistaId) {
        Aluno aluno = alunoService.buscarEntidadePorId(alunoId);
        Nutricionista nutricionista = nutricionistaService.buscarEntidadePorId(nutricionistaId);

        dieta.setAluno(aluno);
        dieta.setNutricionista(nutricionista);

        return dietaRepository.save(dieta);
    }

    // CONSULTAS
    public List<Dieta> listarTodas() {
        return dietaRepository.findAll();
    }

    public Dieta buscarPorId(Long id) {
        return dietaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada com id: " + id));
    }

    public Dieta buscarPorAluno(Long alunoId) {
        return dietaRepository.findByAlunoId(alunoId)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada para o aluno com id: " + alunoId));
    }

    public List<Dieta> buscarPorNutricionista(Long nutricionistaId) {
        return dietaRepository.findByNutricionistaId(nutricionistaId);
    }

    public List<Dieta> buscarDietasAtivas() {
        return dietaRepository.findByAtivaTrue();
    }

    // ATUALIZAÇÕES
    public Dieta atualizarDieta(Long id, Dieta dieta) {
        Dieta existente = dietaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada com id: " + id));

        if (dieta.getNome() != null) existente.setNome(dieta.getNome());
        if (dieta.getDescricao() != null) existente.setDescricao(dieta.getDescricao());
        if (dieta.getDataInicio() != null) existente.setDataInicio(dieta.getDataInicio());
        if (dieta.getDataFim() != null) existente.setDataFim(dieta.getDataFim());
        if (dieta.getObjetivo() != null) existente.setObjetivo(dieta.getObjetivo());
        if (dieta.getAlimentos() != null) existente.setAlimentos(dieta.getAlimentos());

        existente.setAtiva(dieta.isAtiva());

        return dietaRepository.save(existente);
    }

    // STATUS
    public Dieta ativarDieta(Long id) {
        Dieta dieta = dietaRepository.findById(id)
                .or