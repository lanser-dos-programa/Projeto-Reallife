package com.reallife.tcc.controller;

import com.reallife.tcc.dto.AlunoDto;
import com.reallife.tcc.dto.DietaDto;
import com.reallife.tcc.dto.FichaDto;
import com.reallife.tcc.entity.Aulas;
import com.reallife.tcc.service.AlunoService;
import com.reallife.tcc.service.AulaService;
import com.reallife.tcc.service.DietaService;
import com.reallife.tcc.service.FichaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlunoController {

    private final AulaService aulaService;
    private final AlunoService alunoService;
    private final FichaService fichaService;

    @PostMapping
    public ResponseEntity<AlunoDto> cadastrarAluno(@RequestBody AlunoDto alunoDto, @RequestParam Long usuarioId) {
        try {
            AlunoDto alunoSalvo = alunoService.cadastrarAluno(alunoDto, usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AlunoDto>> listarAlunos() {
        List<AlunoDto> alunos = alunoService.listarAlunos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDto> buscarAluno(@PathVariable Long id) {
        try {
            AlunoDto aluno = alunoService.buscarPorId(id);
            return ResponseEntity.ok(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<AlunoDto> buscarAlunoPorUsuario(@PathVariable Long usuarioId) {
        try {
            AlunoDto aluno = alunoService.buscarPorUsuarioId(usuarioId);
            return ResponseEntity.ok(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AlunoDto> buscarAlunoPorEmail(@PathVariable String email) {
        try {
            AlunoDto aluno = alunoService.buscarPorEmail(email);
            return ResponseEntity.ok(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<AlunoDto> buscarAlunoPorMatricula(@PathVariable String matricula) {
        try {
            AlunoDto aluno = alunoService.buscarPorMatricula(matricula);
            return ResponseEntity.ok(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<AlunoDto>> listarAlunosAtivos() {
        List<AlunoDto> alunos = alunoService.listarAlunosAtivos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/inativos")
    public ResponseEntity<List<AlunoDto>> listarAlunosInativos() {
        List<AlunoDto> alunos = alunoService.listarAlunosInativos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/com-plano-nutricional")
    public ResponseEntity<List<AlunoDto>> listarAlunosComPlanoNutricional() {
        List<AlunoDto> alunos = alunoService.listarAlunosComPlanoNutricional();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/sem-plano-nutricional")
    public ResponseEntity<List<AlunoDto>> listarAlunosSemPlanoNutricional() {
        List<AlunoDto> alunos = alunoService.listarAlunosSemPlanoNutricional();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/nutricionista/{nutricionistaId}")
    public ResponseEntity<List<AlunoDto>> listarAlunosPorNutricionista(@PathVariable Long nutricionistaId) {
        List<AlunoDto> alunos = alunoService.listarAlunosPorNutricionista(nutricionistaId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<AlunoDto>> listarAlunosPorProfessor(@PathVariable Long professorId) {
        List<AlunoDto> alunos = alunoService.listarAlunosPorProfessor(professorId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/objetivo/{objetivo}")
    public ResponseEntity<List<AlunoDto>> listarAlunosPorObjetivo(@PathVariable String objetivo) {
        List<AlunoDto> alunos = alunoService.listarAlunosPorObjetivo(objetivo);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/sem-nutricionista")
    public ResponseEntity<List<AlunoDto>> listarAlunosSemNutricionista() {
        List<AlunoDto> alunos = alunoService.listarAlunosSemNutricionista();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/sem-professor")
    public ResponseEntity<List<AlunoDto>> listarAlunosSemProfessor() {
        List<AlunoDto> alunos = alunoService.listarAlunosSemProfessor();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<AlunoDto>> listarAlunosRecentes() {
        List<AlunoDto> alunos = alunoService.listarAlunosRecentes();
        return ResponseEntity.ok(alunos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDto> atualizarAluno(@PathVariable Long id, @RequestBody AlunoDto alunoDto) {
        try {
            AlunoDto alunoAtualizado = alunoService.atualizarAluno(id, alunoDto);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/dados-basicos")
    public ResponseEntity<AlunoDto> atualizarDadosBasicos(@PathVariable Long id, @RequestParam(required = false) Integer idade, @RequestParam(required = false) Double peso, @RequestParam(required = false) Double altura, @RequestParam(required = false) String objetivo, @RequestParam(required = false) String telefone) {
        try {
            AlunoDto alunoAtualizado = alunoService.atualizarDadosBasicos(id, idade, peso, altura, objetivo, telefone);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/medidas")
    public ResponseEntity<AlunoDto> atualizarMedidas(@PathVariable Long id, @RequestParam(required = false) Double peso, @RequestParam(required = false) Double altura) {
        try {
            AlunoDto alunoAtualizado = alunoService.atualizarMedidas(id, peso, altura);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<AlunoDto> ativarAluno(@PathVariable Long id) {
        try {
            AlunoDto alunoAtivado = alunoService.ativarAluno(id);
            return ResponseEntity.ok(alunoAtivado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<AlunoDto> desativarAluno(@PathVariable Long id) {
        try {
            AlunoDto alunoDesativado = alunoService.desativarAluno(id);
            return ResponseEntity.ok(alunoDesativado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/ativar-plano")
    public ResponseEntity<AlunoDto> ativarPlanoNutricional(@PathVariable Long id) {
        try {
            AlunoDto alunoAtualizado = alunoService.ativarPlanoNutricional(id);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/desativar-plano")
    public ResponseEntity<AlunoDto> desativarPlanoNutricional(@PathVariable Long id) {
        try {
            AlunoDto alunoAtualizado = alunoService.desativarPlanoNutricional(id);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        try {
            alunoService.deletarAluno(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/permanentemente")
    public ResponseEntity<Void> deletarAlunoPermanentemente(@PathVariable Long id) {
        try {
            alunoService.deletarAlunoPermanentemente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estatisticas/ativos")
    public ResponseEntity<Long> contarAlunosAtivos() {
        long total = alunoService.contarAlunosAtivos();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/estatisticas/inativos")
    public ResponseEntity<Long> contarAlunosInativos() {
        long total = alunoService.contarAlunosInativos();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/estatisticas/com-plano")
    public ResponseEntity<Long> contarAlunosComPlanoNutricional() {
        long total = alunoService.contarAlunosComPlanoNutricional();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/estatisticas/total")
    public ResponseEntity<Long> contarTotalAlunos() {
        long total = alunoService.contarTotalAlunos();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/busca")
    public ResponseEntity<List<AlunoDto>> buscarComFiltros(
            @RequestParam(required = false) Boolean statusAtivo,
            @RequestParam(required = false) Boolean planoNutricionalAtivo,
            @RequestParam(required = false) Long nutricionistaId,
            @RequestParam(required = false) Long professorId) {

        List<AlunoDto> alunos = alunoService.buscarComFiltros(statusAtivo, planoNutricionalAtivo, nutricionistaId, professorId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/validar/usuario/{usuarioId}")
    public ResponseEntity<Boolean> validarUsuarioAluno(@PathVariable Long usuarioId) {
        boolean existe = alunoService.existeAlunoPorUsuarioId(usuarioId);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/validar/email/{email}")
    public ResponseEntity<Boolean> validarEmailAluno(@PathVariable String email) {
        boolean existe = alunoService.existeAlunoPorEmail(email);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/{id}/status/ativo")
    public ResponseEntity<Boolean> verificarAlunoAtivo(@PathVariable Long id) {
        try {
            boolean ativo = alunoService.isAlunoAtivo(id);
            return ResponseEntity.ok(ativo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/status/plano-ativo")
    public ResponseEntity<Boolean> verificarPlanoAtivo(@PathVariable Long id) {
        try {
            boolean planoAtivo = alunoService.isPlanoNutricionalAtivo(id);
            return ResponseEntity.ok(planoAtivo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{alunoId}/treinos/ativo")
    public ResponseEntity<FichaDto> verTreinoAtivo(@PathVariable Long alunoId) {
        try {
            FichaDto treino = fichaService.buscarFichaAtivaDoAluno(alunoId);
            return ResponseEntity.ok(treino);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{alunoId}/treinos")
    public ResponseEntity<List<FichaDto>> verTodosTreinos(@PathVariable Long alunoId) {
        try {
            List<FichaDto> treinos = fichaService.buscarFichasPorAlunoEStatus(alunoId, null);
            return ResponseEntity.ok(treinos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{alunoId}/treinos/ativos")
    public ResponseEntity<List<FichaDto>> verTreinosAtivos(@PathVariable Long alunoId) {
        try {
            List<FichaDto> treinos = fichaService.buscarFichasPorAlunoEStatus(alunoId, true);
            return ResponseEntity.ok(treinos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{alunoId}/treinos/inativos")
    public ResponseEntity<List<FichaDto>> verTreinosInativos(@PathVariable Long alunoId) {
        try {
            List<FichaDto> treinos = fichaService.buscarFichasPorAlunoEStatus(alunoId, false);
            return ResponseEntity.ok(treinos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{alunoId}/treinos/{fichaId}")
    public ResponseEntity<FichaDto> verTreinoPorId(@PathVariable Long alunoId, @PathVariable Long fichaId) {
        try {
            FichaDto ficha = fichaService.buscarPorId(fichaId);

            if (!ficha.getAlunoId().equals(alunoId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.ok(ficha);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    private final DietaService dietaService;

// ========== DIETA DO ALUNO ==========


    @GetMapping("/{alunoId}/dieta")
    public ResponseEntity<DietaDto> verMinhaDieta(@PathVariable Long alunoId) {
        try {
            DietaDto dieta = dietaService.buscarPorAluno(alunoId);
            return ResponseEntity.ok(dieta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Aulas ---
    @GetMapping("/aulas/disponiveis")
    public ResponseEntity<List<Aulas>> verAulasDisponiveis() {
        try {
            List<Aulas> aulas = aulaService.listarProximasAulas();
            return ResponseEntity.ok(aulas);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/aulas/{aulaId}")
    public ResponseEntity<Aulas> verDetalhesAula(@PathVariable Long aulaId) {
        try {
            Aulas aula = aulaService.buscarPorId(aulaId);
            return ResponseEntity.ok(aula);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}