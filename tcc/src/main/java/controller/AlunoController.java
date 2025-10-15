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

    // Cadastrar novo aluno (feito pela recepção)
    @PostMapping
    public ResponseEntity<Aluno> cadastrarAluno(@RequestBody Aluno aluno) {
        Aluno novoAluno = alunoService.cadastrarAluno(aluno);
        return ResponseEntity.ok(novoAluno);
    }

    // Listar todos os alunos (admin/recepção)
    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos() {
        List<Aluno> alunos = alunoService.listarAlunos();
        return ResponseEntity.ok(alunos);
    }

    // Buscar aluno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    // Atualizar dados do aluno (nome, email, plano ativo etc.)
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @RequestBody Aluno dadosAtualizados) {
        Aluno atualizado = alunoService.atualizarAluno(id, dadosAtualizados);
        return ResponseEntity.ok(atualizado);
    }

    // Deletar aluno (admin/recepção)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }

    // Ativar plano de nutrição
    @PutMapping("/{id}/ativar-plano-nutricional")
    public ResponseEntity<Aluno> ativarPlanoNutricional(@PathVariable Long id) {
        Aluno aluno = alunoService.ativarPlanoNutricional(id);
        return ResponseEntity.ok(aluno);
    }

    // Desativar plano de nutrição
    @PutMapping("/{id}/desativar-plano-nutricional")
    public ResponseEntity<Aluno> desativarPlanoNutricional(@PathVariable Long id) {
        Aluno aluno = alunoService.desativarPlanoNutricional(id);
        return ResponseEntity.ok(aluno);
    }
}
