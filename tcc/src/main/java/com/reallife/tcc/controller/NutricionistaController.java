package com.reallife.tcc.controller;

import com.reallife.tcc.dto.AlunoDto;
import com.reallife.tcc.entity.Dieta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.reallife.tcc.service.AlunoService;
import com.reallife.tcc.service.DietaService;

import java.util.List;

@RestController
@RequestMapping("/nutricionista")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NutricionistaController {

    private final AlunoService alunoService;
    private final DietaService dietaService;

    // Listar alunos com plano nutricional ativo
    @GetMapping("/alunos")
    public ResponseEntity<List<AlunoDto>> listarAlunosComPlanoNutricional() {
        List<AlunoDto> alunos = alunoService.listarAlunosComPlanoNutricional();
        return ResponseEntity.ok(alunos);
    }

    // Listar todos os alunos do nutricionista
    @GetMapping("/alunos/todos")
    public ResponseEntity<List<AlunoDto>> listarTodosAlunos() {
        List<AlunoDto> alunos = alunoService.listarAlunos();
        return ResponseEntity.ok(alunos);
    }

    // Buscar aluno específico
    @GetMapping("/alunos/{id}")
    public ResponseEntity<?> buscarAlunoPorId(@PathVariable Long id) {
        try {
            AlunoDto aluno = alunoService.buscarPorId(id);

            if (!aluno.isPlanoNutricionalAtivo()) {
                return ResponseEntity.badRequest().body("Aluno não possui plano nutricional ativo");
            }

            return ResponseEntity.ok(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar aluno por nome
    @GetMapping("/alunos/buscar")
    public ResponseEntity<List<AlunoDto>> buscarAlunoPorNome(@RequestParam String nome) {
        List<AlunoDto> alunos = alunoService.listarAlunosPorNome(nome);
        return ResponseEntity.ok(alunos);
    }

    // Atribuir ou atualizar dieta para um aluno
    @PostMapping("/alunos/{id}/dieta")
    public ResponseEntity<?> atribuirDieta(@PathVariable Long id, @RequestBody Dieta dieta) {
        try {
            AlunoDto aluno = alunoService.buscarPorId(id);

            if (!aluno.isPlanoNutricionalAtivo()) {
                return ResponseEntity.badRequest().body("Aluno não possui plano nutricional ativo");
            }

            // Aqui você precisa implementar o método no DietaService
            Dieta novaDieta = dietaService.salvarOuAtualizarDieta(id, dieta);
            return ResponseEntity.ok(novaDieta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Visualizar dieta atual do aluno
    @GetMapping("/alunos/{id}/dieta")
    public ResponseEntity<?> verDieta(@PathVariable Long id) {
        try {
            Dieta dieta = dietaService.buscarPorAluno(id);
            return ResponseEntity.ok(dieta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar todas as dietas
    @GetMapping("/dietas")
    public ResponseEntity<List<Dieta>> listarTodasDietas() {
        List<Dieta> dietas = dietaService.listarTodas();
        return ResponseEntity.ok(dietas);
    }

    // Remover dieta do aluno
    @DeleteMapping("/alunos/{id}/dieta")
    public ResponseEntity<Void> removerDieta(@PathVariable Long id) {
        try {
            dietaService.deletarDietaPorAluno(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Ativar plano nutricional
    @PutMapping("/alunos/{id}/ativar-plano")
    public ResponseEntity<?> ativarPlanoNutricional(@PathVariable Long id) {
        try {
            AlunoDto aluno = alunoService.ativarPlanoNutricional(id);
            return ResponseEntity.ok(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Desativar plano nutricional
    @PutMapping("/alunos/{id}/desativar-plano")
    public ResponseEntity<?> desativarPlanoNutricional(@PathVariable Long id) {
        try {
            AlunoDto aluno = alunoService.desativarPlanoNutricional(id);
            return ResponseEntity.ok(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Vincular nutricionista ao aluno
    @PutMapping("/alunos/{alunoId}/vincular")
    public ResponseEntity<?> vincularAluno(@PathVariable Long alunoId,
                                           @RequestParam Long nutricionistaId) {
        try {
            // Você precisa implementar este método no AlunoService
            AlunoDto aluno = alunoService.vincularNutricionista(alunoId, nutricionistaId);
            return ResponseEntity.ok(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Desvincular aluno
    @PutMapping("/alunos/{alunoId}/desvincular")
    public ResponseEntity<?> desvincularAluno(@PathVariable Long alunoId) {
        try {
            AlunoDto aluno = alunoService.desvincularNutricionista(alunoId);
            return ResponseEntity.ok(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar alunos sem nutricionista
    @GetMapping("/alunos/sem-vinculo")
    public ResponseEntity<List<AlunoDto>> listarAlunosSemNutricionista() {
        List<AlunoDto> alunos = alunoService.listarAlunosSemNutricionista();
        return ResponseEntity.ok(alunos);
    }

    // Estatísticas do nutricionista
    @GetMapping("/estatisticas")
    public ResponseEntity<?> obterEstatisticas(@RequestParam Long nutricionistaId) {
        try {
            long totalAlunos = alunoService.contarAlunosPorNutricionista(nutricionistaId);
            long alunosComPlano = alunoService.contarAlunosComPlanoNutricional();
            long totalDietas = dietaService.contarDietasPorNutricionista(nutricionistaId);

            // Retorna um objeto com as estatísticas
            return ResponseEntity.ok(new EstatisticasNutricionista(
                    totalAlunos, alunosComPlano, totalDietas
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao obter estatísticas");
        }
    }

    // Classe interna para estatísticas
    private static class EstatisticasNutricionista {
        public long totalAlunos;
        public long alunosComPlano;
        public long totalDietas;

        public EstatisticasNutricionista(long totalAlunos, long alunosComPlano, long totalDietas) {
            this.totalAlunos = totalAlunos;
            this.alunosComPlano = alunosComPlano;
            this.totalDietas = totalDietas;
        }
    }

    // Atualizar dados básicos do aluno
    @PatchMapping("/alunos/{id}/dados")
    public ResponseEntity<?> atualizarDadosAluno(@PathVariable Long id, @RequestParam(required = false) Integer idade, @RequestParam(required = false) Double peso, @RequestParam(required = false) Double altura, @RequestParam(required = false) String objetivo, @RequestParam(required = false) String telefone) {
        try {
            AlunoDto alunoAtualizado = alunoService.atualizarDadosBasicos(id, idade, peso, altura, objetivo, telefone);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}