package com.reallife.tcc.controller;

import com.reallife.tcc.dto.*;
import com.reallife.tcc.entity.Recepcao;
import com.reallife.tcc.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.reallife.tcc.security.Role;
import com.reallife.tcc.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recepcao")
@RequiredArgsConstructor
public class RecepcaoController {

    private final RecepcaoService recepcaoService;
    private final UsuarioService usuarioService;
    private final AlunoService alunoService;
    private final ProfessorService professorService;
    private final NutricionistaService nutricionistaService;

    // ========== CRUD RECEPCIONISTA ==========

    @PostMapping
    public ResponseEntity<Recepcao> cadastrar(@RequestBody Recepcao recepcao) {
        Recepcao novo = recepcaoService.cadastrarRecepcionista(recepcao);
        return ResponseEntity.ok(novo);
    }

    @GetMapping
    public ResponseEntity<List<Recepcao>> listarTodos() {
        List<Recepcao> lista = recepcaoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recepcao> buscarPorId(@PathVariable Long id) {
        Recepcao recepcionista = recepcaoService.buscarPorId(id);
        return ResponseEntity.ok(recepcionista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recepcao> atualizar(@PathVariable Long id, @RequestBody Recepcao dadosAtualizados) {
        Recepcao atualizado = recepcaoService.atualizar(id, dadosAtualizados);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        recepcaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Recepcao> login(@RequestParam String email, @RequestParam String senha) {
        Recepcao recepcionista = recepcaoService.login(email, senha);
        return ResponseEntity.ok(recepcionista);
    }

    // ========== GERENCIAMENTO COMPLETO DE USUÁRIOS ==========

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/usuarios/buscar")
    public ResponseEntity<List<Usuario>> buscarUsuario(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Role role) {
        List<Usuario> usuarios = usuarioService.buscarUsuariosComFiltros(nome, email, role);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.criar(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    @PutMapping("/definir-role/{id}")
    public ResponseEntity<Usuario> definirRole(
            @PathVariable Long id,
            @RequestParam Role role) {
        Usuario atualizado = usuarioService.definirRole(id, role);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/usuarios/{id}/status")
    public ResponseEntity<Usuario> alterarStatusUsuario(
            @PathVariable Long id,
            @RequestParam boolean ativo) {
        Usuario atualizado = usuarioService.alterarStatus(id, ativo);
        return ResponseEntity.ok(atualizado);
    }

    @PutMapping("/usuarios/{id}/senha")
    public ResponseEntity<Void> redefinirSenha(
            @PathVariable Long id,
            @RequestParam String novaSenha) {
        usuarioService.redefinirSenha(id, novaSenha);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ========== GERENCIAMENTO DE ALUNOS ==========

    @GetMapping("/alunos")
    public ResponseEntity<List<AlunoDto>> listarTodosAlunos() {
        List<AlunoDto> alunos = alunoService.listarAlunos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/alunos/{id}")
    public ResponseEntity<AlunoDto> buscarAluno(@PathVariable Long id) {
        AlunoDto aluno = alunoService.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    @PostMapping("/alunos")
    public ResponseEntity<AlunoDto> criarAluno(@RequestBody AlunoDto alunoDto, @RequestParam Long usuarioId) {
        AlunoDto alunoSalvo = alunoService.cadastrarAluno(alunoDto, usuarioId);
        return ResponseEntity.ok(alunoSalvo);
    }

    @PatchMapping("/alunos/{id}/ativar")
    public ResponseEntity<AlunoDto> ativarAluno(@PathVariable Long id) {
        AlunoDto alunoAtivado = alunoService.ativarAluno(id);
        return ResponseEntity.ok(alunoAtivado);
    }

    @PatchMapping("/alunos/{id}/desativar")
    public ResponseEntity<AlunoDto> desativarAluno(@PathVariable Long id) {
        AlunoDto alunoDesativado = alunoService.desativarAluno(id);
        return ResponseEntity.ok(alunoDesativado);
    }

    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/alunos/{id}/permanentemente")
    public ResponseEntity<Void> deletarAlunoPermanentemente(@PathVariable Long id) {
        alunoService.deletarAlunoPermanentemente(id);
        return ResponseEntity.noContent().build();
    }

    // ========== GERENCIAMENTO DE PROFESSORES ==========

    @GetMapping("/professores")
    public ResponseEntity<List<ProfessorDto>> listarTodosProfessores() {
        List<ProfessorDto> professores = professorService.listarProfessores();
        return ResponseEntity.ok(professores);
    }

    @GetMapping("/professores/{id}")
    public ResponseEntity<ProfessorDto> buscarProfessor(@PathVariable Long id) {
        ProfessorDto professor = professorService.buscarPorId(id);
        return ResponseEntity.ok(professor);
    }

    @PostMapping("/professores")
    public ResponseEntity<ProfessorDto> criarProfessor(@RequestBody ProfessorDto professorDto, @RequestParam Long usuarioId) {
        ProfessorDto professorSalvo = professorService.cadastrarProfessor(professorDto, usuarioId);
        return ResponseEntity.ok(professorSalvo);
    }

    @PatchMapping("/professores/{id}/ativar")
    public ResponseEntity<ProfessorDto> ativarProfessor(@PathVariable Long id) {
        ProfessorDto professorAtivado = professorService.ativarProfessor(id);
        return ResponseEntity.ok(professorAtivado);
    }

    @PatchMapping("/professores/{id}/desativar")
    public ResponseEntity<ProfessorDto> desativarProfessor(@PathVariable Long id) {
        ProfessorDto professorDesativado = professorService.desativarProfessor(id);
        return ResponseEntity.ok(professorDesativado);
    }

    @DeleteMapping("/professores/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        professorService.deletarProfessor(id);
        return ResponseEntity.noContent().build();
    }

    // ========== GERENCIAMENTO DE NUTRICIONISTAS ==========

    @GetMapping("/nutricionistas")
    public ResponseEntity<List<NutricionistaDto>> listarTodosNutricionistas() {
        List<NutricionistaDto> nutricionistas = nutricionistaService.listarNutricionistas();
        return ResponseEntity.ok(nutricionistas);
    }

    @GetMapping("/nutricionistas/{id}")
    public ResponseEntity<NutricionistaDto> buscarNutricionista(@PathVariable Long id) {
        NutricionistaDto nutricionista = nutricionistaService.buscarPorId(id);
        return ResponseEntity.ok(nutricionista);
    }

    @PostMapping("/nutricionistas")
    public ResponseEntity<NutricionistaDto> criarNutricionista(@RequestBody NutricionistaDto nutricionistaDto, @RequestParam Long usuarioId) {
        NutricionistaDto nutricionistaSalvo = nutricionistaService.cadastrarNutricionista(nutricionistaDto, usuarioId);
        return ResponseEntity.ok(nutricionistaSalvo);
    }

    @PatchMapping("/nutricionistas/{id}/ativar")
    public ResponseEntity<NutricionistaDto> ativarNutricionista(@PathVariable Long id) {
        NutricionistaDto nutricionistaAtivado = nutricionistaService.ativarNutricionista(id);
        return ResponseEntity.ok(nutricionistaAtivado);
    }

    @PatchMapping("/nutricionistas/{id}/desativar")
    public ResponseEntity<NutricionistaDto> desativarNutricionista(@PathVariable Long id) {
        NutricionistaDto nutricionistaDesativado = nutricionistaService.desativarNutricionista(id);
        return ResponseEntity.ok(nutricionistaDesativado);
    }

    @DeleteMapping("/nutricionistas/{id}")
    public ResponseEntity<Void> deletarNutricionista(@PathVariable Long id) {
        nutricionistaService.deletarNutricionista(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ESTATÍSTICAS E RELATÓRIOS ==========

    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticasGerais() {
        Map<String, Object> estatisticas = new HashMap<>();

        // Contagem por tipo
        estatisticas.put("totalUsuarios", usuarioService.contarTotalUsuarios());
        estatisticas.put("totalAlunos", alunoService.contarTotalAlunos());
        estatisticas.put("totalProfessores", professorService.contarTotalProfessores());
        estatisticas.put("totalNutricionistas", nutricionistaService.contarTotalNutricionistas());
        estatisticas.put("totalRecepcionistas", recepcaoService.contarTotalRecepcionistas());

        // Status
        estatisticas.put("alunosAtivos", alunoService.contarAlunosAtivos());
        estatisticas.put("alunosInativos", alunoService.contarAlunosInativos());
        estatisticas.put("professoresAtivos", professorService.contarProfessoresAtivos());
        estatisticas.put("nutricionistasAtivos", nutricionistaService.contarNutricionistasAtivos());

        return ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/estatisticas/usuarios-por-role")
    public ResponseEntity<Map<String, Long>> contarUsuariosPorRole() {
        Map<String, Long> contagem = usuarioService.contarUsuariosPorRole();
        return ResponseEntity.ok(contagem);
    }

    // ========== OPERAÇÕES DE RECEPÇÃO (TÍPICAS) ==========

    @GetMapping("/alunos/sem-professor")
    public ResponseEntity<List<AlunoDto>> listarAlunosSemProfessor() {
        List<AlunoDto> alunos = alunoService.listarAlunosSemProfessor();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/alunos/sem-nutricionista")
    public ResponseEntity<List<AlunoDto>> listarAlunosSemNutricionista() {
        List<AlunoDto> alunos = alunoService.listarAlunosSemNutricionista();
        return ResponseEntity.ok(alunos);
    }

    @PatchMapping("/alunos/{alunoId}/vincular-professor")
    public ResponseEntity<AlunoDto> vincularProfessor(
            @PathVariable Long alunoId,
            @RequestParam Long professorId) {
        AlunoDto alunoAtualizado = alunoService.vincularProfessor(alunoId, professorId);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @PatchMapping("/alunos/{alunoId}/vincular-nutricionista")
    public ResponseEntity<AlunoDto> vincularNutricionista(
            @PathVariable Long alunoId,
            @RequestParam Long nutricionistaId) {
        AlunoDto alunoAtualizado = alunoService.vincularNutricionista(alunoId, nutricionistaId);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();

        // Estatísticas rápidas
        dashboard.put("totalAlunos", alunoService.contarTotalAlunos());
        dashboard.put("alunosAtivos", alunoService.contarAlunosAtivos());
        dashboard.put("totalProfessores", professorService.contarTotalProfessores());
        dashboard.put("totalNutricionistas", nutricionistaService.contarTotalNutricionistas());

        // Listas recentes
        dashboard.put("alunosRecentes", alunoService.listarAlunosRecentes());

        return ResponseEntity.ok(dashboard);
    }
}