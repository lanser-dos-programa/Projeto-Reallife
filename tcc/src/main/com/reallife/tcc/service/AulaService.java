package com.reallife.tcc.service;

import com.reallife.tcc.entity.Aulas;
import com.reallife.tcc.entity.Professor;
import com.reallife.tcc.repository.AulasRepository;
import com.reallife.tcc.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AulaService {

    private final AulasRepository aulaRepository;
    private final ProfessorRepository professorRepository;

    public Aulas criarAula(Aulas aula) {
        // Validar se professor existe
        Professor professor = professorRepository.findById(aula.getProfessor().getId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        aula.setProfessor(professor);

        // Validar conflito de horário
        validarConflitoHorario(aula);

        // Validar se data não é passada
        if (aula.getData().isBefore(LocalDate.now())) {
            throw new RuntimeException("Não é possível criar aula com data passada");
        }

        // Validar se hora de término é depois da hora de início
        if (aula.getHoraTermino().isBefore(aula.getHoraInicio()) ||
                aula.getHoraTermino().equals(aula.getHoraInicio())) {
            throw new RuntimeException("Hora de término deve ser posterior à hora de início");
        }

        aula.setAtiva(true);
        return aulaRepository.save(aula);
    }

    private void validarConflitoHorario(Aulas aula) {
        boolean conflito = aulaRepository.existsByDataAndHoraInicioAndLocal(
                aula.getData(), aula.getHoraInicio(), aula.getLocal());

        if (conflito) {
            throw new RuntimeException("Já existe uma aula neste horário e local");
        }
    }

    private boolean existeConflitoExcetoAulaAtual(Aulas aula, Long aulaId) {
        List<Aulas> aulasConflitantes = aulaRepository
                .findByDataAndHoraInicioAndLocal(
                        aula.getData(),
                        aula.getHoraInicio(),
                        aula.getLocal()
                )
                .stream()
                .filter(a -> !a.getId().equals(aulaId))
                .toList();

        return !aulasConflitantes.isEmpty();
    }

    public List<Aulas> listarProximasAulas() {
        return aulaRepository.findProximasAulas(LocalDate.now());
    }

    public List<Aulas> listarAulasHoje() {
        return aulaRepository.findAulasHoje();
    }

    public List<Aulas> listarAulasDaSemana() {
        LocalDate hoje = LocalDate.now();
        LocalDate segunda = hoje.minusDays(hoje.getDayOfWeek().getValue() - 1);
        LocalDate domingo = segunda.plusDays(6);
        return aulaRepository.findAulasSemana(segunda, domingo);
    }

    public List<Aulas> listarAulasDoProfessor(Long professorId) {
        professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        return aulaRepository.findByProfessorId(professorId);
    }

    public List<Aulas> listarAulasDoProfessorNoDia(Long professorId, LocalDate data) {
        professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        return aulaRepository.findByProfessorIdAndData(professorId, data);
    }

    public Aulas buscarPorId(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada com ID: " + id));
    }

    public List<Aulas> buscarPorNome(String nome) {
        return aulaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Aulas> buscarPorLocal(String local) {
        return aulaRepository.findByLocal(local);
    }

    public List<Aulas> buscarPorData(LocalDate data) {
        return aulaRepository.findByData(data);
    }

    public List<Aulas> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return aulaRepository.findByDataBetween(inicio, fim);
    }

    public void deletarAula(Long id) {
        if (!aulaRepository.existsById(id)) {
            throw new RuntimeException("Aula não encontrada para deletar");
        }
        aulaRepository.deleteById(id);
    }

    public Aulas atualizarAula(Long id, Aulas dados) {
        Aulas existente = buscarPorId(id);

        // Validar se professor foi alterado
        if (dados.getProfessor() != null && dados.getProfessor().getId() != null) {
            Professor professor = professorRepository.findById(dados.getProfessor().getId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            existente.setProfessor(professor);
        }

        // Verificar conflito se horário/local mudou
        if ((dados.getData() != null && !dados.getData().equals(existente.getData())) ||
                (dados.getHoraInicio() != null && !dados.getHoraInicio().equals(existente.getHoraInicio())) ||
                (dados.getLocal() != null && !dados.getLocal().equals(existente.getLocal()))) {

            Aulas aulaTemporaria = new Aulas();
            aulaTemporaria.setData(dados.getData() != null ? dados.getData() : existente.getData());
            aulaTemporaria.setHoraInicio(dados.getHoraInicio() != null ? dados.getHoraInicio() : existente.getHoraInicio());
            aulaTemporaria.setLocal(dados.getLocal() != null ? dados.getLocal() : existente.getLocal());

            if (existeConflitoExcetoAulaAtual(aulaTemporaria, id)) {
                throw new RuntimeException("Já existe outra aula neste horário e local");
            }
        }

        existente.setNome(dados.getNome() != null ? dados.getNome() : existente.getNome());
        existente.setDescricao(dados.getDescricao() != null ? dados.getDescricao() : existente.getDescricao());
        existente.setData(dados.getData() != null ? dados.getData() : existente.getData());
        existente.setHoraInicio(dados.getHoraInicio() != null ? dados.getHoraInicio() : existente.getHoraInicio());
        existente.setHoraTermino(dados.getHoraTermino() != null ? dados.getHoraTermino() : existente.getHoraTermino());
        existente.setLocal(dados.getLocal() != null ? dados.getLocal() : existente.getLocal());
        existente.setNivel(dados.getNivel() != null ? dados.getNivel() : existente.getNivel());
        existente.setAtiva(dados.isAtiva());

        return aulaRepository.save(existente);
    }

    public Aulas ativarAula(Long id) {
        Aulas aula = buscarPorId(id);
        aula.setAtiva(true);
        return aulaRepository.save(aula);
    }

    public Aulas desativarAula(Long id) {
        Aulas aula = buscarPorId(id);
        aula.setAtiva(false);
        return aulaRepository.save(aula);
    }

    public List<Aulas> listarAulasAtivas() {
        return aulaRepository.findByAtivaTrue();
    }

    public List<Aulas> listarAulasInativas() {
        return aulaRepository.findByAtivaFalse();
    }

    public long contarAulasPorProfessor(Long professorId) {
        professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        return aulaRepository.countByProfessorId(professorId);
    }

    public boolean verificarDisponibilidade(LocalDate data, LocalTime horaInicio, String local) {
        return !aulaRepository.existsByDataAndHoraInicioAndLocal(data, horaInicio, local);
    }
}