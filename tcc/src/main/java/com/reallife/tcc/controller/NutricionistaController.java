package com.reallife.tcc.controller;

import com.reallife.tcc.dto.AlimentoDto;
import com.reallife.tcc.dto.AlunoDto;
import com.reallife.tcc.dto.DietaDto;
import com.reallife.tcc.dto.NutricionistaDto;
import com.reallife.tcc.service.AlimentosImportService;
import com.reallife.tcc.service.AlunoService;
import com.reallife.tcc.service.DietaService;
import com.reallife.tcc.service.NutricionistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nutricionista")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NutricionistaController {

    private final AlunoService alunoService;
    private final DietaService dietaService;
    private final NutricionistaService nutricionistaService;
    private final AlimentosImportService automacaoAlimentoService;

    // ========== NUTRICIONISTAS ==========
    @GetMapping
    public ResponseEntity<List<NutricionistaDto>> listarNutricionistas() {
        return ResponseEntity.ok(nutricionistaService.listarNutricionistas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutricionistaDto> buscarNutricionista(@PathVariable Long id) {
        return ResponseEntity.ok(nutricionistaService.buscarPorId(id));
    }

    // ========== ALUNOS ==========
    @GetMapping("/alunos")
    public ResponseEntity<List<AlunoDto>> listarAlunosComPlanoNutricional() {
        return ResponseEntity.ok(alunoService.listarAlunosComPlanoNutricional());
    }

    @GetMapping("/alunos/todos")
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

    @GetMapping("/alunos/sem-vinculo")
    public ResponseEntity<List<AlunoDto>> listarAlunosSemNutricionista() {
        return ResponseEntity.ok(alunoService.listarAlunosSemNutricionista());
    }

    // ========== VINCULAÇÃO DE ALUNOS ==========
    @PutMapping("/alunos/{alunoId}/vincular")
    public ResponseEntity<AlunoDto> vincularAluno(@PathVariable Long alunoId,
                                                  @RequestParam Long nutricionistaId) {
        return ResponseEntity.ok(alunoService.vincularNutricionista(alunoId, nutricionistaId));
    }

    @PutMapping("/alunos/{alunoId}/desvincular")
    public ResponseEntity<AlunoDto> desvincularAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(alunoService.desvincularNutricionista(alunoId));
    }

    // ========== PLANOS NUTRICIONAIS ==========
    @PutMapping("/alunos/{id}/ativar-plano")
    public ResponseEntity<AlunoDto> ativarPlanoNutricional(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.ativarPlanoNutricional(id));
    }

    @PutMapping("/alunos/{id}/desativar-plano")
    public ResponseEntity<AlunoDto> desativarPlanoNutricional(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.desativarPlanoNutricional(id));
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

    // ========== DIETAS (CONTROLADO PELO NUTRICIONISTA) ==========
    @PostMapping("/alunos/{alunoId}/dietas")
    public ResponseEntity<DietaDto> criarDietaParaAluno(
            @PathVariable Long alunoId,
            @RequestParam Long nutricionistaId,
            @RequestBody DietaDto dietaDto) {
        DietaDto dietaSalva = dietaService.criarDietaParaAluno(alunoId, nutricionistaId, dietaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dietaSalva);
    }

    @GetMapping("/{nutricionistaId}/dietas")
    public ResponseEntity<List<DietaDto>> listarDietasDoNutricionista(@PathVariable Long nutricionistaId) {
        return ResponseEntity.ok(dietaService.buscarPorNutricionista(nutricionistaId));
    }

    @GetMapping("/dietas/{dietaId}")
    public ResponseEntity<DietaDto> buscarDieta(@PathVariable Long dietaId) {
        return ResponseEntity.ok(dietaService.buscarPorId(dietaId));
    }

    @GetMapping("/alunos/{alunoId}/dieta")
    public ResponseEntity<DietaDto> buscarDietaDoAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(dietaService.buscarPorAluno(alunoId));
    }

    @PutMapping("/dietas/{dietaId}")
    public ResponseEntity<DietaDto> atualizarDieta(@PathVariable Long dietaId, @RequestBody DietaDto dietaDto) {
        return ResponseEntity.ok(dietaService.atualizarDieta(dietaId, dietaDto));
    }

    @PostMapping("/dietas/{dietaId}/alimentos")
    public ResponseEntity<DietaDto> adicionarAlimentos(@PathVariable Long dietaId,
                                                       @RequestBody List<Long> alimentosIds) {
        return ResponseEntity.ok(dietaService.adicionarAlimentos(dietaId, alimentosIds));
    }

    @DeleteMapping("/dietas/{dietaId}/alimentos")
    public ResponseEntity<DietaDto> removerAlimentos(@PathVariable Long dietaId,
                                                     @RequestBody List<Long> alimentosIds) {
        return ResponseEntity.ok(dietaService.removerAlimentos(dietaId, alimentosIds));
    }

    @PatchMapping("/dietas/{dietaId}/ativar")
    public ResponseEntity<DietaDto> ativarDieta(@PathVariable Long dietaId) {
        return ResponseEntity.ok(dietaService.ativarDieta(dietaId));
    }

    @PatchMapping("/dietas/{dietaId}/desativar")
    public ResponseEntity<DietaDto> desativarDieta(@PathVariable Long dietaId) {
        return ResponseEntity.ok(dietaService.desativarDieta(dietaId));
    }

    @DeleteMapping("/dietas/{dietaId}")
    public ResponseEntity<Void> deletarDieta(@PathVariable Long dietaId) {
        dietaService.deletarDieta(dietaId);
        return ResponseEntity.noContent().build();
    }

    // ========== 🎯 AUTOMAÇÃO DE ALIMENTOS ==========
    @GetMapping("/sugestoes-alimentos")
    public ResponseEntity<List<AlimentoDto>> obterSugestoesAlimentos(
            @RequestParam String categoria,
            @RequestParam String objetivo) {
        List<AlimentoDto> sugestoes = automacaoAlimentoService
                .sugerirAlimentosPorCategoria(categoria, objetivo);
        return ResponseEntity.ok(sugestoes);
    }

    @PostMapping("/calcular-nutrientes")
    public ResponseEntity<Map<String, Double>> calcularNutrientes(@RequestBody List<Long> alimentosIds) {
        Map<String, Double> nutrientes = automacaoAlimentoService
                .calcularNutrientesAutomaticamente(alimentosIds);
        return ResponseEntity.ok(nutrientes);
    }

    @GetMapping("/combinar-alimentos")
    public ResponseEntity<List<AlimentoDto>> sugerirCombinacao(
            @RequestParam Double caloriasDesejadas,
            @RequestParam(required = false) String restricoes) {
        List<AlimentoDto> combinacao = automacaoAlimentoService
                .sugerirCombinacao(caloriasDesejadas, restricoes);
        return ResponseEntity.ok(combinacao);
    }

    @GetMapping("/alimentos/categoria/{categoria}")
    public ResponseEntity<List<AlimentoDto>> listarAlimentosPorCategoria(@PathVariable String categoria) {
        List<AlimentoDto> alimentos = automacaoAlimentoService.listarAlimentosPorCategoria(categoria);
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/alimentos/buscar")
    public ResponseEntity<List<AlimentoDto>> buscarAlimentosPorNome(@RequestParam String nome) {
        List<AlimentoDto> alimentos = automacaoAlimentoService.buscarAlimentosPorNome(nome);
        return ResponseEntity.ok(alimentos);
    }

    // ========== ESTATÍSTICAS ==========
    @GetMapping("/{nutricionistaId}/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticas(@PathVariable Long nutricionistaId) {
        NutricionistaDto nutricionista = nutricionistaService.buscarPorId(nutricionistaId);
        long totalAlunos = alunoService.contarAlunosPorNutricionista(nutricionistaId);
        long alunosComPlano = alunoService.contarAlunosComPlanoNutricional();
        long totalDietas = dietaService.contarDietasPorNutricionista(nutricionistaId);
        long dietasAtivas = dietaService.contarDietasAtivas();

        Map<String, Object> estatisticas = new HashMap<>();
        estatisticas.put("nutricionista", nutricionista);
        estatisticas.put("totalAlunos", totalAlunos);
        estatisticas.put("alunosComPlano", alunosComPlano);
        estatisticas.put("totalDietas", totalDietas);
        estatisticas.put("dietasAtivas", dietasAtivas);

        return ResponseEntity.ok(estatisticas);
    }
}