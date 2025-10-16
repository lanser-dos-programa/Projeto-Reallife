package controller;

import entity.Aluno;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AlunoService;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    // Cadastrar novo aluno
    @PostMapping
    public ResponseEntity<Aluno> cadastrarAluno(@RequestBody Aluno aluno) {
        Aluno novoAluno = alunoService.cadastrarAluno(aluno);
        return ResponseEntity.ok(novoAluno);
    }

    // Listar alunos
    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos() {
        return ResponseEntity.ok(alunoService.listarAlunos());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.buscarPorId(id));
    }

    // Atualizar dados do aluno
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @RequestBody Aluno dadosAtualizados) {
        return ResponseEntity.ok(alunoService.atualizarAluno(id, dadosAtualizados));
    }

    // Deletar aluno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }

    // Ativar plano nutricional
    @PutMapping("/{id}/ativar-plano-nutricional")
    public ResponseEntity<Aluno> ativarPlanoNutricional(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.ativarPlanoNutricional(id));
    }

    // Desativar plano nutricional
    @PutMapping("/{id}/desativar-plano-nutricional")
    public ResponseEntity<Aluno> desativarPlanoNutricional(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.desativarPlanoNutricional(id));
    }
}
