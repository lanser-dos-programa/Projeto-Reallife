package com.reallife.tcc.controller;

import com.reallife.tcc.dto.*;
import com.reallife.tcc.entity.Exercicio;
import com.reallife.tcc.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final AlunoService alunoService;
    private final FichaService fichaService;
    private final ProfessorService professorService;
    private final ExercicioService exercicioService;

    // ========== PERFIL DO PROFESSOR ==========
    @GetMapping("/perfil/{id}")
    public ResponseEntity<ProfessorDto> buscarPerfilProfessor(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.buscarPorId(id));
    }

    @GetMapping("/perfil/usuario/{usuarioId}")
    public ResponseEntity<ProfessorDto> buscarProfessorPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(professorService.buscarPorUsuarioId(usuarioId));
    }

    @PutMapping("/perfil/{id}")
    public ResponseEntity<ProfessorDto> atualizarPerfil(@PathVariable Long id, @RequestBody ProfessorDto professorDto) {
        return ResponseEntity.ok(professorService.atualizarProfessor(id, professorDto));
    }

    @PatchMapping("/perfil/{id}/ativar")
    public ResponseEntity<ProfessorDto> ativarProfessor(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.ativarProfessor(id));
    }

    @PatchMapping("/perfil/{id}/desativar")
    public ResponseEntity<ProfessorDto> desativarProfessor(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.desativarProfessor(id));
    }

    // ========== GERENCIAMENTO DE ALUNOS ==========
    @GetMapping("/alunos")
    public ResponseEntity<List<AlunoDto>> listarTodosAlunos() {
        return ResponseEntity.ok(alunoService.listarAlunos());
    }

    @GetMapping("/alunos/{id}")
    public ResponseEntity<AlunoDto> buscarAlunoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.buscarPorId(id));
    }

    @GetMapping("/alunos/buscar")
    public ResponseEntity<List<AlunoDto>> buscarAlunoPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(alunoService.listarAlunosPorNome(nome));
    }

    @GetMapping("/alunos/meus-alunos/{professorId}")
    public ResponseEntity<List<AlunoDto>> listarMeusAlunos(@PathVariable Long professorId) {
        return ResponseEntity.ok(alunoService.listarAlunosPorProfessor(professorId));
    }

    @GetMapping("/alunos/sem-vinculo")
    public ResponseEntity<List<AlunoDto>> listarAlunosSemProfessor() {
        return ResponseEntity.ok(alunoService.listarAlunosSemProfessor());
    }

    @GetMapping("/alunos/ativos")
    public ResponseEntity<List<AlunoDto>> listarAlunosAtivos() {
        return ResponseEntity.ok(alunoService.listarAlunosAtivos());
    }

    // ========== VINCULAÇÃO DE ALUNOS ==========
    @PutMapping("/alunos/{alunoId}/vincular")
    public ResponseEntity<AlunoDto> vincularAluno(@PathVariable Long alunoId, @RequestParam Long professorId) {
        return ResponseEntity.ok(alunoService.vincularProfessor(alunoId, professorId));
    }

    @PutMapping("/alunos/{alunoId}/desvincular")
    public ResponseEntity<AlunoDto> desvincularAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(alunoService.desvincularProfessor(alunoId));
    }

    // ========== ATUALIZAÇÕES DE ALUNOS ==========
    @PatchMapping("/alunos/{id}/dados")
    public ResponseEntity<AlunoDto> atualizarDadosAluno(
            @PathVariable Long id,
            @RequestParam(required = false) Integer idade,
            @RequestParam(required = false) Double peso,
            @RequestParam(required = false) Double altura,
            @RequestParam(required = false) String objetivo,
            @RequestParam(required = false) String telefone) {
        return ResponseEntity.ok(alunoService.atualizarDadosBasicos(id, idade, peso, altura, objetivo, telefone));
    }

    @PatchMapping("/alunos/{id}/medidas")
    public ResponseEntity<AlunoDto> atualizarMedidasAluno(
            @PathVariable Long id,
            @RequestParam(required = false) Double peso,
            @RequestParam(required = false) Double altura) {
        return ResponseEntity.ok(alunoService.atualizarMedidas(id, peso, altura));
    }

    @PatchMapping("/alunos/{id}/ativar")
    public ResponseEntity<AlunoDto> ativarAluno(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.ativarAluno(id));
    }

    @PatchMapping("/alunos/{id}/desativar")
    public ResponseEntity<AlunoDto> desativarAluno(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.desativarAluno(id));
    }

    // ========== GERENCIAMENTO DE FICHAS/TREINOS ==========
    @PostMapping("/alunos/{alunoId}/fichas")
    public ResponseEntity<FichaDto> criarFichaParaAluno(
            @PathVariable Long alunoId,
            @RequestParam Long professorId,
            @RequestBody FichaDto fichaDto) {
        FichaDto fichaSalva = fichaService.criarFichaParaAluno(alunoId, professorId, fichaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fichaSalva);
    }

    @GetMapping("/{professorId}/fichas")
    public ResponseEntity<List<FichaDto>> listarFichasDoProfessor(@PathVariable Long professorId) {
        return ResponseEntity.ok(fichaService.buscarPorProfessor(professorId));
    }

    @GetMapping("/fichas/{fichaId}")
    public ResponseEntity<FichaDto> buscarFicha(@PathVariable Long fichaId) {
        return ResponseEntity.ok(fichaService.buscarPorId(fichaId));
    }

    @GetMapping("/alunos/{alunoId}/ficha")
    public ResponseEntity<FichaDto> buscarFichaDoAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(fichaService.buscarPorAluno(alunoId));
    }

    @PutMapping("/fichas/{fichaId}")
    public ResponseEntity<FichaDto> atualizarFicha(@PathVariable Long fichaId, @RequestBody FichaDto fichaDto) {
        return ResponseEntity.ok(fichaService.atualizarFicha(fichaId, fichaDto));
    }

    @PostMapping("/fichas/{fichaId}/exercicios")
    public ResponseEntity<FichaDto> adicionarExercicios(@PathVariable Long fichaId,
                                                        @RequestBody List<Long> exerciciosIds) {
        return ResponseEntity.ok(fichaService.adicionarExercicios(fichaId, exerciciosIds));
    }

    @DeleteMapping("/fichas/{fichaId}/exercicios")
    public ResponseEntity<FichaDto> removerExercicios(@PathVariable Long fichaId,
                                                      @RequestBody List<Long> exerciciosIds) {
        return ResponseEntity.ok(fichaService.removerExercicios(fichaId, exerciciosIds));
    }

    @PatchMapping("/fichas/{fichaId}/ativar")
    public ResponseEntity<FichaDto> ativarFicha(@PathVariable Long fichaId) {
        return ResponseEntity.ok(fichaService.ativarFicha(fichaId));
    }

    @DeleteMapping("/fichas/{fichaId}")
    public ResponseEntity<Void> deletarFicha(@PathVariable Long fichaId) {
        fichaService.deletarFicha(fichaId);
        return ResponseEntity.noContent().build();
    }

    // ========== GERENCIAMENTO DE EXERCÍCIOS ==========
    @GetMapping("/exercicios")
    public ResponseEntity<List<Exercicio>> listarExercicios() {
        return ResponseEntity.ok(exercicioService.listarExercicios());
    }

    @GetMapping("/exercicios/{id}")
    public ResponseEntity<Exercicio> buscarExercicio(@PathVariable Long id) {
        return ResponseEntity.ok(exercicioService.buscarPorId(id));
    }

    // ========== ESTATÍSTICAS E RELATÓRIOS ==========
    @GetMapping("/estatisticas/{professorId}/total-alunos")
    public ResponseEntity<Long> contarTotalAlunos(@PathVariable Long professorId) {
        long total = alunoService.contarAlunosPorProfessor(professorId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/estatisticas/{professorId}/alunos-ativos")
    public ResponseEntity<Long> contarAlunosAtivos(@PathVariable Long professorId) {
        // Você pode precisar criar este método no AlunoService
        long total = alunoService.contarAlunosAtivos();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/estatisticas/{professorId}/total-fichas")
    public ResponseEntity<Long> contarTotalFichas(@PathVariable Long professorId) {
        // Você pode precisar criar este método no FichaService
        // long total = fichaService.contarFichasPorProfessor(professorId);
        return ResponseEntity.ok(0L); // Placeholder
    }

    // ========== DASHBOARD ==========
    @GetMapping("/dashboard/{professorId}")
    public ResponseEntity<Map<String, Object>> getDashboard(@PathVariable Long professorId) {
        Map<String, Object> dashboard = new HashMap<>();

        // Estatísticas básicas
        dashboard.put("totalAlunos", alunoService.contarAlunosPorProfessor(professorId));
        dashboard.put("alunosAtivos", alunoService.contarAlunosAtivos());
        // dashboard.put("totalFichas", fichaService.contarFichasPorProfessor(professorId));

        // Listas recentes
        dashboard.put("alunosRecentes", alunoService.listarAlunosRecentes());
        // dashboard.put("fichasRecentes", fichaService.listarFichasRecentes(professorId));

        return ResponseEntity.ok(dashboard);
    }

    // ========== VALIDAÇÕES ==========
    @GetMapping("/validar/email/{email}")
    public ResponseEntity<Boolean> validarEmailProfessor(@PathVariable String email) {
        boolean existe = professorService.existeProfessorPorEmail(email);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/validar/usuario/{usuarioId}")
    public ResponseEntity<Boolean> validarUsuarioProfessor(@PathVariable Long usuarioId) {
        boolean existe = professorService.existeProfessorPorUsuarioId(usuarioId);
        return ResponseEntity.ok(existe);
    }
}