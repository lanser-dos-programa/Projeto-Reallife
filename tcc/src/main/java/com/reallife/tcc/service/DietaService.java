package com.reallife.tcc.service;

import com.reallife.tcc.dto.DietaDto;
import com.reallife.tcc.entity.*;
import com.reallife.tcc.repository.AlunoRepository;
import com.reallife.tcc.repository.DietaRepository;
import com.reallife.tcc.repository.NutricionistaRepository;
import com.reallife.tcc.repository.AlimentosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DietaService {

    private final DietaRepository dietaRepository;
    private final AlunoRepository alunoRepository;
    private final NutricionistaRepository nutricionistaRepository;
    private final AlimentosRepository alimentosRepository;

    // CONVERSÕES DTO/ENTITY
    public DietaDto toDto(Dieta dieta) {
        // Calcular totais nutricionais
        Double totalCalorias = 0.0;
        Double totalProteinas = 0.0;
        Double totalCarboidratos = 0.0;
        Double totalGorduras = 0.0;
        Integer totalAlimentos = 0;

        if (dieta.getAlimentos() != null && !dieta.getAlimentos().isEmpty()) {
            totalCalorias = dieta.getAlimentos().stream()
                    .mapToDouble(Alimentos::getCalorias)
                    .sum();
            totalProteinas = dieta.getAlimentos().stream()
                    .mapToDouble(Alimentos::getProteinas)
                    .sum();
            totalCarboidratos = dieta.getAlimentos().stream()
                    .mapToDouble(Alimentos::getCarboidratos)
                    .sum();
            totalGorduras = dieta.getAlimentos().stream()
                    .mapToDouble(Alimentos::getGorduras)
                    .sum();
            totalAlimentos = dieta.getAlimentos().size();
        }

        return DietaDto.builder()
                .id(dieta.getId())
                .nome(dieta.getNome())
                .descricao(dieta.getDescricao())
                .dataInicio(dieta.getDataInicio())
                .dataFim(dieta.getDataFim())
                .objetivo(dieta.getObjetivo())
                .ativa(dieta.isAtiva())
                .alunoId(dieta.getAluno() != null ? dieta.getAluno().getId() : null)
                .alunoNome(dieta.getAluno() != null ? dieta.getAluno().getUsuario().getNome() : null)
                .nutricionistaId(dieta.getNutricionista() != null ? dieta.getNutricionista().getId() : null)
                .nutricionistaNome(dieta.getNutricionista() != null ? dieta.getNutricionista().getUsuario().getNome() : null)
                .alimentosIds(dieta.getAlimentos() != null ?
                        dieta.getAlimentos().stream().map(Alimentos::getId).collect(Collectors.toList()) : null)
                .alimentosNomes(dieta.getAlimentos() != null ?
                        dieta.getAlimentos().stream().map(Alimentos::getNome).collect(Collectors.toList()) : null)
                .totalCalorias(totalCalorias)
                .totalProteinas(totalProteinas)
                .totalCarboidratos(totalCarboidratos)
                .totalGorduras(totalGorduras)
                .totalAlimentos(totalAlimentos)
                .build();
    }

    public Dieta toEntity(DietaDto dietaDto) {
        Dieta dieta = new Dieta();
        dieta.setId(dietaDto.getId());
        dieta.setNome(dietaDto.getNome());
        dieta.setDescricao(dietaDto.getDescricao());
        dieta.setDataInicio(dietaDto.getDataInicio());
        dieta.setDataFim(dietaDto.getDataFim());
        dieta.setObjetivo(dietaDto.getObjetivo());
        dieta.setAtiva(dietaDto.isAtiva());

        // Busca e define o aluno
        if (dietaDto.getAlunoId() != null) {
            Aluno aluno = alunoRepository.findById(dietaDto.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + dietaDto.getAlunoId()));
            dieta.setAluno(aluno);
        }

        // Busca e define o nutricionista
        if (dietaDto.getNutricionistaId() != null) {
            Nutricionista nutricionista = nutricionistaRepository.findById(dietaDto.getNutricionistaId())
                    .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com id: " + dietaDto.getNutricionistaId()));
            dieta.setNutricionista(nutricionista);
        }

        // Busca e define os alimentos
        if (dietaDto.getAlimentosIds() != null && !dietaDto.getAlimentosIds().isEmpty()) {
            List<Alimentos> alimentos = alimentosRepository.findAllById(dietaDto.getAlimentosIds());
            dieta.setAlimentos(alimentos);
        }

        return dieta;
    }

    // CADASTRO
    public DietaDto criarDieta(DietaDto dietaDto) {
        Dieta dieta = toEntity(dietaDto);
        Dieta dietaSalva = dietaRepository.save(dieta);
        return toDto(dietaSalva);
    }

    public Dieta salvarOuAtualizarDieta(Long alunoId, Dieta dieta) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + alunoId));

        // Desativa dieta anterior se existir
        dietaRepository.findByAlunoIdAndAtivaTrue(alunoId).ifPresent(dietaAnterior -> {
            dietaAnterior.setAtiva(false);
            dietaRepository.save(dietaAnterior);
        });

        dieta.setAluno(aluno);
        if (aluno.getNutricionista() != null) {
            dieta.setNutricionista(aluno.getNutricionista());
        }

        return dietaRepository.save(dieta);
    }

    public DietaDto criarDietaParaAluno(Long alunoId, Long nutricionistaId, DietaDto dietaDto) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + alunoId));

        Nutricionista nutricionista = nutricionistaRepository.findById(nutricionistaId)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com id: " + nutricionistaId));

        // Desativa dieta anterior
        dietaRepository.findByAlunoIdAndAtivaTrue(alunoId).ifPresent(dietaAnterior -> {
            dietaAnterior.setAtiva(false);
            dietaRepository.save(dietaAnterior);
        });

        Dieta dieta = toEntity(dietaDto);
        dieta.setAluno(aluno);
        dieta.setNutricionista(nutricionista);
        dieta.setAtiva(true);

        Dieta dietaSalva = dietaRepository.save(dieta);
        return toDto(dietaSalva);
    }

    // CONSULTAS
    public List<DietaDto> listarTodas() {
        return dietaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public DietaDto buscarPorId(Long id) {
        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada com id: " + id));
        return toDto(dieta);
    }

    public Dieta buscarEntidadePorId(Long id) {
        return dietaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada com id: " + id));
    }

    public DietaDto buscarPorAluno(Long alunoId) {
        Dieta dieta = dietaRepository.findByAlunoIdAndAtivaTrue(alunoId)
                .orElseThrow(() -> new RuntimeException("Dieta ativa não encontrada para o aluno com id: " + alunoId));
        return toDto(dieta);
    }

    public List<DietaDto> buscarPorNutricionista(Long nutricionistaId) {
        return dietaRepository.findByNutricionistaId(nutricionistaId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<DietaDto> buscarDietasAtivas() {
        return dietaRepository.findByAtivaTrue()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<DietaDto> buscarDietasInativas() {
        return dietaRepository.findByAtivaFalse()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<DietaDto> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return dietaRepository.findByPeriodo(dataInicio, dataFim)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<DietaDto> buscarDietasAtivasNaData(LocalDate data) {
        return dietaRepository.findDietasAtivasNaData(data)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<DietaDto> buscarRecentes() {
        return dietaRepository.findTop10Recent()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ATUALIZAÇÕES
    public DietaDto atualizarDieta(Long id, DietaDto dietaDto) {
        Dieta existente = dietaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada com id: " + id));

        if (dietaDto.getNome() != null) existente.setNome(dietaDto.getNome());
        if (dietaDto.getDescricao() != null) existente.setDescricao(dietaDto.getDescricao());
        if (dietaDto.getDataInicio() != null) existente.setDataInicio(dietaDto.getDataInicio());
        if (dietaDto.getDataFim() != null) existente.setDataFim(dietaDto.getDataFim());
        if (dietaDto.getObjetivo() != null) existente.setObjetivo(dietaDto.getObjetivo());

        existente.setAtiva(dietaDto.isAtiva());

        // Atualiza alimentos se fornecidos
        if (dietaDto.getAlimentosIds() != null) {
            List<Alimentos> alimentos = alimentosRepository.findAllById(dietaDto.getAlimentosIds());
            existente.setAlimentos(alimentos);
        }

        Dieta dietaAtualizada = dietaRepository.save(existente);
        return toDto(dietaAtualizada);
    }

    // STATUS
    public DietaDto ativarDieta(Long id) {
        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada com id: " + id));
        dieta.setAtiva(true);
        Dieta dietaAtualizada = dietaRepository.save(dieta);
        return toDto(dietaAtualizada);
    }

    public DietaDto desativarDieta(Long id) {
        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada com id: " + id));
        dieta.setAtiva(false);
        Dieta dietaAtualizada = dietaRepository.save(dieta);
        return toDto(dietaAtualizada);
    }

    // EXCLUSÃO
    public void deletarDieta(Long id) {
        if (!dietaRepository.existsById(id)) {
            throw new RuntimeException("Dieta não encontrada para exclusão.");
        }
        dietaRepository.deleteById(id);
    }

    public void deletarDietaPorAluno(Long alunoId) {
        dietaRepository.findByAlunoIdAndAtivaTrue(alunoId).ifPresent(dieta -> {
            dieta.setAtiva(false);
            dietaRepository.save(dieta);
        });
    }

    // ESTATÍSTICAS
    public long contarDietasAtivas() {
        return dietaRepository.countByAtivaTrue();
    }

    public long contarDietasInativas() {
        return dietaRepository.countByAtivaFalse();
    }

    public long contarDietasPorNutricionista(Long nutricionistaId) {
        return dietaRepository.countByNutricionistaId(nutricionistaId);
    }

    public long contarTotalDietas() {
        return dietaRepository.count();
    }

    // VALIDAÇÕES
    public boolean existeDietaAtivaPorAluno(Long alunoId) {
        return dietaRepository.existsByAlunoIdAndAtivaTrue(alunoId);
    }

    public boolean existeDietaPorId(Long id) {
        return dietaRepository.existsById(id);
    }

    // ADICIONAR/REMOVER ALIMENTOS
    public DietaDto adicionarAlimentos(Long dietaId, List<Long> alimentosIds) {
        Dieta dieta = dietaRepository.findById(dietaId)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada com id: " + dietaId));

        List<Alimentos> novosAlimentos = alimentosRepository.findAllById(alimentosIds);
        List<Alimentos> alimentosAtuais = dieta.getAlimentos();

        if (alimentosAtuais == null) {
            dieta.setAlimentos(novosAlimentos);
        } else {
            alimentosAtuais.addAll(novosAlimentos);
            dieta.setAlimentos(alimentosAtuais);
        }

        Dieta dietaAtualizada = dietaRepository.save(dieta);
        return toDto(dietaAtualizada);
    }

    public DietaDto removerAlimentos(Long dietaId, List<Long> alimentosIds) {
        Dieta dieta = dietaRepository.findById(dietaId)
                .orElseThrow(() -> new RuntimeException("Dieta não encontrada com id: " + dietaId));

        if (dieta.getAlimentos() != null) {
            List<Alimentos> alimentosAtuais = dieta.getAlimentos();
            alimentosAtuais.removeIf(alimento -> alimentosIds.contains(alimento.getId()));
            dieta.setAlimentos(alimentosAtuais);
        }

        Dieta dietaAtualizada = dietaRepository.save(dieta);
        return toDto(dietaAtualizada);
    }
}