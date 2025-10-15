package controller;

import entity.Aluno;
import entity.Dietas;
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

    // Listar alunos que têm o plano de nutrição ativo
    @GetMapping("/alunos")
    public ResponseEntity<List<Aluno>> listarAlunosComPlanoNutricional() {
        List<Aluno> alunos = alunoService.listarAlunosComPlanoNutricional();
        return ResponseEntity.ok(alunos);
    }

    // Buscar aluno específico (só pra visualizar dados básicos e dieta associada)
    @GetMapping("/alunos/{id}")
    public ResponseEntity<Aluno> buscarAlunoPorId(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (!aluno.PlanoNutricionalAtivo()) {
            return ResponseEntity.badRequest().build(); // não tem acesso à nutrição
        }

        return ResponseEntity.ok(aluno);
    }

    // Atribuir ou atualizar dieta para um aluno já existente
    @PostMapping("/alunos/{id}/dieta")
    public ResponseEntity<Dietas> atribuirDieta(@PathVariable Long id, @RequestBody Dieta dieta) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (!aluno.isPlanoNutricionalAtivo()) {
            return ResponseEntity.badRequest().build();
        }

        Dieta novaDieta = dietaService.salvarOuAtualizarDieta(aluno, dieta);
        return ResponseEntity.ok(novaDieta);
    }

    // Visualizar dieta atual de um aluno
    @GetMapping("/alunos/{id}/dieta")
    public ResponseEntity<Dieta> verDieta(@PathVariable Long id) {
        Dieta dieta = dietaService.buscarPorAluno(id);
        return ResponseEntity.ok(dieta);
    }

    // Remover dieta de um aluno (se for necessário resetar)
    @DeleteMapping("/alunos/{id}/dieta")
    public ResponseEntity<Void> removerDieta(@PathVariable Long id) {
        dietaService.deletarDietaPorAluno(id);
        return ResponseEntity.noContent().build();
    }
}
