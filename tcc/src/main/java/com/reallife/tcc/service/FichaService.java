package com.reallife.tcc.service;

import com.reallife.tcc.dto.FichaDto;
import com.reallife.tcc.entity.Aluno;
import com.reallife.tcc.entity.Exercicio;
import com.reallife.tcc.entity.FichaDeTreino;
import com.reallife.tcc.entity.Professor;
import com.reallife.tcc.repository.AlunoRepository;
import com.reallife.tcc.repository.FichaRepository;
import com.reallife.tcc.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FichaService {

    private final FichaRepository fichaRepository;
    private final ExercicioService exercicioService;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;

    // ========== CONVERSÕES DTO/ENTITY ==========
    public FichaDto toDto(FichaDeTreino ficha) {
        FichaDto dto = new FichaDto();
        dto.setNome(ficha.getNome());
        dto.setObjetivo(ficha.getObjetivo());
        dto.setAlunoId(ficha.getAluno().getId());
        dto.setExerciciosIds(ficha.getExercicios()
                .stream()
                .map(Exercicio::getId)
                .collect(Collectors.toList()));
        return dto;
    }

    public FichaDeTreino toEntity(FichaDto dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Buscar o professor através do aluno
        Professor professor = aluno.getProfessor();
        if (professor == null) {
            throw new RuntimeException("Aluno não possui professor vinculado");
        }

        Set<Exercicio> exercicios = dto.getExerciciosIds()
                .stream()
                .map(exercicioService::buscarPorId)
                .collect(Collectors.toSet());

        FichaDeTreino ficha = new FichaDeTreino();
        ficha.setNome(dto.getNome());
        ficha.setObjetivo(dto.getObjetivo());
        ficha.setAluno(aluno);
        ficha.setProfessor(professor);
        ficha.setExercicios(exercicios);

        return ficha;
    }

    // ========== CRIAÇÃO DE FICHAS ==========
    public FichaDto criarFichaParaAluno(Long alunoId, Long professorId, FichaDto fichaDto) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + alunoId));

        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + professorId));

        // Verificar se o aluno pertence ao professor
        if (!aluno.getProfessor().getId().equals(professorId)) {
            throw new RuntimeException("Aluno não está vinculado a este professor");
        }

        FichaDeTreino ficha = toEntity(fichaDto);
        ficha.setAluno(aluno);
        ficha.setProfessor(professor);

        FichaDeTreino fichaSalva = fichaRepository.save(ficha);
        return toDto(fichaSalva);
    }

    public FichaDeTreino criarFicha(FichaDto dto) {
        FichaDeTreino ficha = toEntity(dto);
        return fichaRepository.save(ficha);
    }

    // ========== CONSULTAS ==========
    public FichaDto buscarPorId(Long fichaId) {
        FichaDeTreino ficha = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada com id: " + fichaId));
        return toDto(ficha);
    }

    public FichaDto buscarPorAluno(Long alunoId) {
        FichaDeTreino ficha = fichaRepository.findByAlunoId(alunoId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada para o aluno com id: " + alunoId));
        return toDto(ficha);
    }

    public List<FichaDto> buscarPorProfessor(Long professorId) {
        return fichaRepository.findByAlunoProfessorId(professorId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<FichaDto> listarTodasFichas() {
        return fichaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ========== ATUALIZAÇÕES ==========
    public FichaDto atualizarFicha(Long fichaId, FichaDto fichaDto) {
        FichaDeTreino fichaExistente = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada com id: " + fichaId));

        if (fichaDto.getNome() != null) {
            fichaExistente.setNome(fichaDto.getNome());
        }
        if (fichaDto.getObjetivo() != null) {
            fichaExistente.setObjetivo(fichaDto.getObjetivo());
        }

        FichaDeTreino fichaAtualizada = fichaRepository.save(fichaExistente);
        return toDto(fichaAtualizada);
    }

    // ========== GERENCIAMENTO DE EXERCÍCIOS ==========
    public FichaDto adicionarExercicios(Long fichaId, List<Long> exerciciosIds) {
        FichaDeTreino ficha = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada com id: " + fichaId));

        Set<Exercicio> novosExercicios = exerciciosIds.stream()
                .map(exercicioService::buscarPorId)
                .collect(Collectors.toSet());

        ficha.getExercicios().addAll(novosExercicios);

        FichaDeTreino fichaAtualizada = fichaRepository.save(ficha);
        return toDto(fichaAtualizada);
    }

    public FichaDto removerExercicios(Long fichaId, List<Long> exerciciosIds) {
        FichaDeTreino ficha = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada com id: " + fichaId));

        Set<Exercicio> exerciciosParaRemover = exerciciosIds.stream()
                .map(exercicioService::buscarPorId)
                .collect(Collectors.toSet());

        ficha.getExercicios().removeAll(exerciciosParaRemover);

        FichaDeTreino fichaAtualizada = fichaRepository.save(ficha);
        return toDto(fichaAtualizada);
    }

    // ========== STATUS DA FICHA ==========
    public FichaDto ativarFicha(Long fichaId) {
        FichaDeTreino ficha = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada com id: " + fichaId));

        // Desativar outras fichas do aluno
        List<FichaDeTreino> fichasDoAluno = fichaRepository.findAllByAlunoId(ficha.getAluno().getId());

        for (FichaDeTreino f : fichasDoAluno) {
            f.setAtiva(false);
        }

        // Ativar a ficha atual
        ficha.setAtiva(true);

        fichaRepository.saveAll(fichasDoAluno);
        FichaDeTreino fichaAtivada = fichaRepository.save(ficha);
        return toDto(fichaAtivada);
    }

    public FichaDto desativarFicha(Long fichaId) {
        FichaDeTreino ficha = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada com id: " + fichaId));

        ficha.setAtiva(false);

        FichaDeTreino fichaDesativada = fichaRepository.save(ficha);
        return toDto(fichaDesativada);
    }

    // ========== EXCLUSÃO ==========
    public void deletarFicha(Long fichaId) {
        FichaDeTreino ficha = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada com id: " + fichaId));
        fichaRepository.delete(ficha);
    }

    // ========== VALIDAÇÕES ==========
    public boolean existeFichaPorAluno(Long alunoId) {
        return fichaRepository.existsByAlunoId(alunoId);
    }

    public boolean isFichaAtiva(Long fichaId) {
        FichaDeTreino ficha = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada com id: " + fichaId));
        return ficha.getAtiva() != null && ficha.getAtiva();
    }

    public boolean existeFichaAtivaPorAluno(Long alunoId) {
        return fichaRepository.existsFichaAtivaByAlunoId(alunoId);
    }

    // ========== ESTATÍSTICAS ==========
    public long contarTotalFichas() {
        return fichaRepository.count();
    }

    public long contarFichasAtivas() {
        return fichaRepository.findAll()
                .stream()
                .filter(ficha -> ficha.getAtiva() != null && ficha.getAtiva())
                .count();
    }

    public long contarFichasInativas() {
        return fichaRepository.findAll()
                .stream()
                .filter(ficha -> ficha.getAtiva() == null || !ficha.getAtiva())
                .count();
    }

    public long contarFichasPorProfessor(Long professorId) {
        return fichaRepository.findByAlunoProfessorId(professorId).size();
    }

    public long contarFichasAtivasPorProfessor(Long professorId) {
        return fichaRepository.findByAlunoProfessorId(professorId)
                .stream()
                .filter(ficha -> ficha.getAtiva() != null && ficha.getAtiva())
                .count();
    }

    public Map<String, Long> obterEstatisticasCompletas(Long professorId) {
        Map<String, Long> estatisticas = new HashMap<>();

        estatisticas.put("totalFichas", contarTotalFichas());
        estatisticas.put("fichasAtivas", contarFichasAtivas());
        estatisticas.put("fichasInativas", contarFichasInativas());
        estatisticas.put("fichasProfessor", contarFichasPorProfessor(professorId));
        estatisticas.put("fichasAtivasProfessor", contarFichasAtivasPorProfessor(professorId));

        return estatisticas;
    }

    public Map<String, Object> obterEstatisticasDetalhadas(Long professorId) {
        Map<String, Object> estatisticas = new HashMap<>();

        // Estatísticas básicas
        estatisticas.put("totalFichas", contarTotalFichas());
        estatisticas.put("fichasAtivas", contarFichasAtivas());
        estatisticas.put("fichasInativas", contarFichasInativas());
        estatisticas.put("fichasProfessor", contarFichasPorProfessor(professorId));
        estatisticas.put("fichasAtivasProfessor", contarFichasAtivasPorProfessor(professorId));

        // Fichas recentes
        List<FichaDto> fichasRecentes = fichaRepository.findTop10ByOrderByDataCriacaoDesc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        estatisticas.put("fichasRecentes", fichasRecentes);

        // Distribuição por objetivo
        Map<String, Long> fichasPorObjetivo = fichaRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        ficha -> ficha.getObjetivo() != null ? ficha.getObjetivo() : "Sem objetivo",
                        Collectors.counting()
                ));
        estatisticas.put("fichasPorObjetivo", fichasPorObjetivo);

        return estatisticas;
    }

    public List<FichaDto> listarFichasRecentes() {
        return fichaRepository.findTop10ByOrderByDataCriacaoDesc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<FichaDto> listarFichasPorObjetivo(String objetivo) {
        return fichaRepository.findByObjetivoContainingIgnoreCase(objetivo)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ========== MÉTODOS COM FILTROS ==========
    public List<FichaDto> buscarFichasComFiltros(Boolean ativa, Long professorId, String objetivo) {
        List<FichaDeTreino> fichas;

        if (professorId != null) {
            fichas = fichaRepository.findByAlunoProfessorId(professorId);
        } else {
            fichas = fichaRepository.findAll();
        }

        return fichas.stream()
                .filter(ficha -> ativa == null ||
                        (ficha.getAtiva() != null && ficha.getAtiva() == ativa))
                .filter(ficha -> objetivo == null ||
                        (ficha.getObjetivo() != null && ficha.getObjetivo().toLowerCase().contains(objetivo.toLowerCase())))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ========== RELATÓRIOS ==========
    public Map<String, Object> gerarRelatorioFichas(Long professorId) {
        Map<String, Object> relatorio = new HashMap<>();

        // Estatísticas básicas
        relatorio.put("estatisticas", obterEstatisticasCompletas(professorId));

        // Lista de fichas do professor
        List<FichaDto> fichasProfessor = buscarPorProfessor(professorId);
        relatorio.put("fichas", fichasProfessor);

        // Fichas ativas vs inativas
        Map<String, Long> statusFichas = new HashMap<>();
        long ativasCount = fichasProfessor.stream()
                .filter(f -> {
                    try {
                        FichaDeTreino ficha = fichaRepository.findByAlunoId(f.getAlunoId())
                                .orElseThrow(() -> new RuntimeException("Ficha não encontrada"));
                        return ficha.getAtiva() != null && ficha.getAtiva();
                    } catch (Exception e) {
                        return false;
                    }
                })
                .count();

        statusFichas.put("ativas", ativasCount);
        statusFichas.put("inativas", (long) fichasProfessor.size() - ativasCount);
        relatorio.put("statusFichas", statusFichas);

        return relatorio;
    }

    // ========== MÉTODOS ADICIONAIS ==========
    public List<FichaDto> buscarFichasPorAlunoEStatus(Long alunoId, Boolean ativa) {
        List<FichaDeTreino> fichas = fichaRepository.findAllByAlunoId(alunoId);

        return fichas.stream()
                .filter(ficha -> ativa == null ||
                        (ficha.getAtiva() != null && ficha.getAtiva() == ativa))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public FichaDto buscarFichaAtivaDoAluno(Long alunoId) {
        FichaDeTreino ficha = fichaRepository.findFichaAtivaByAlunoId(alunoId)
                .orElseThrow(() -> new RuntimeException("Nenhuma ficha ativa encontrada para o aluno"));
        return toDto(ficha);
    }

    public boolean verificarFichaAtivaAluno(Long alunoId) {
        return fichaRepository.existsFichaAtivaByAlunoId(alunoId);
    }

    // ========== MÉTODOS DE BUSCA AVANÇADA ==========
    public List<FichaDto> buscarFichasPorExercicio(Long exercicioId) {
        return fichaRepository.findByExercicioId(exercicioId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<FichaDto> buscarFichasPorNomeExercicio(String nomeExercicio) {
        return fichaRepository.findByNomeExercicioContaining(nomeExercicio)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}