package controller;

import entity.Aluno;
import entity.Dieta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AlunoService;
import service.DietaService;

import java.util.List;

@RestController
@RequestMapping("/nutricionista")
@RequiredArgsConstructor
public class NutricionistaController {

    private final AlunoService alunoService;
    private final DietaService dietaService;

    // Listar alunos com plano nutricional ativo
    @GetMapping("/alunos")
    public ResponseEntity<List<Aluno>> listarAlunosComPlanoNutricional() {
        List<Aluno> alunos = alunoService.listarAlunosComPlanoNutricional();
        return ResponseEntity.ok(alunos);
    }

    // Buscar aluno específico (com dieta)
    @GetMapping("/alunos/{id}")
    public ResponseEntity<?> buscarAlunoPorId(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (!aluno.isPlanoNutricionalAtivo()) {
            return ResponseEntity.badRequest().body("Aluno não possui plano nutricional ativo");
        }

        return ResponseEntity.ok(aluno);
    }

    // Atribuir ou atualizar dieta para um aluno
    @PostMapping("/alunos/{id}/dieta")
    public ResponseEntity<?> atribuirDieta(@PathVariable Long id, @RequestBody Dieta dieta) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (!aluno.isPlanoNutricionalAtivo()) {
            return ResponseEntity.badRequest().body("Aluno não possui plano nutricional ativo");
        }

        Dieta novaDieta = dietaService.salvarOuAtualizarDieta(aluno, dieta);
        return ResponseEntity.ok(novaDieta);
    }

    // Visualizar dieta atual do aluno
    @GetMapping("/alunos/{id}/dieta")
    public ResponseEntity<Dieta> verDieta(@PathVariable Long id) {
        Dieta dieta = dietaService.buscarPorAluno(id);
        return ResponseEntity.ok(dieta);
    }

    // Remover dieta do aluno
    @DeleteMapping("/alunos/{id}/dieta")
    public ResponseEntity<Void> removerDieta(@PathVariable Long id) {
        dietaService.deletarDietaPorAluno(id);
        return ResponseEntity.noContent().build();
    }

    // Ativar plano nutricional
    @PutMapping("/alunos/{id}/ativar-plano")
    public ResponseEntity<Aluno> ativarPlanoNutricional(@PathVariable Long id) {
        Aluno aluno = alunoService.ativarPlanoNutricional(id);
        return ResponseEntity.ok(aluno);
    }

    // Desativar plano nutricional
    @PutMapping("/alunos/{id}/desativar-plano")
    public ResponseEntity<Aluno> desativarPlanoNutricional(@PathVariable Long id) {
        Aluno aluno = alunoService.desativarPlanoNutricional(id);
        return ResponseEntity.ok(aluno);
    }
}
