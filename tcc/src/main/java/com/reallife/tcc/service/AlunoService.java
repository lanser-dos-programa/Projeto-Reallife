package com.reallife.tcc.service;

import com.reallife.tcc.dto.AlunoDto;
import com.reallife.tcc.entity.Aluno;
import com.reallife.tcc.entity.Nutricionista;
import com.reallife.tcc.entity.Professor;
import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.repository.AlunoRepository;
import com.reallife.tcc.repository.NutricionistaRepository;
import com.reallife.tcc.repository.ProfessorRepository;
import com.reallife.tcc.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final UsuarioRepository usuarioRepository;
    private final NutricionistaRepository nutricionistaRepository;
    private final ProfessorRepository professorRepository;

    // CONVERSÕES DTO/ENTITY
    public AlunoDto toDto(Aluno aluno) {
        return AlunoDto.builder()
                .id(aluno.getId())
                .idade(aluno.getIdade())
                .peso(aluno.getPeso())
                .altura(aluno.getAltura())
                .matricula(aluno.getMatricula())
                .telefone(aluno.getTelefone())
                .objetivo(aluno.getObjetivo())
                .statusAtivo(aluno.isStatusAtivo())
                .planoNutricionalAtivo(aluno.isPlanoNutricionalAtivo())
                .nome(aluno.getUsuario().getNome())
                .email(aluno.getUsuario().getEmail())
                .cpf(aluno.getUsuario().getCpf())
                .usuarioId(aluno.getUsuario().getId())
                .nutricionistaId(aluno.getNutricionista() != null ? aluno.getNutricionista().getId() : null)
                .professorId(aluno.getProfessor() != null ? aluno.getProfessor().getId() : null)
                .nomeNutricionista(aluno.getNutricionista() != null ? aluno.getNutricionista().getUsuario().getNome() : null)
                .nomeProfessor(aluno.getProfessor() != null ? aluno.getProfessor().getUsuario().getNome() : null)
                .build();
    }

    public Aluno toEntity(AlunoDto alunoDto) {
        return Aluno.builder()
                .id(alunoDto.getId())
                .idade(alunoDto.getIdade())
                .peso(alunoDto.getPeso())
                .altura(alunoDto.getAltura())
                .matricula(alunoDto.getMatricula())
                .telefone(alunoDto.getTelefone())
                .objetivo(alunoDto.getObjetivo())
                .statusAtivo(alunoDto.isStatusAtivo())
                .planoNutricionalAtivo(alunoDto.isPlanoNutricionalAtivo())
                .build();
    }

    // CADASTRO
    public AlunoDto cadastrarAluno(AlunoDto alunoDto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + usuarioId));

        if (alunoRepository.existsByUsuarioId(usuarioId)) {
            throw new RuntimeException("Este usuário já está vinculado a um aluno.");
        }

        Aluno aluno = toEntity(alunoDto);
        aluno.setUsuario(usuario);
        Aluno alunoSalvo = alunoRepository.save(aluno);
        return toDto(alunoSalvo);
    }

    // CONSULTAS
    public List<AlunoDto> listarAlunos() {
        return alunoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AlunoDto buscarPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
        return toDto(aluno);
    }

    public Aluno buscarEntidadePorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
    }

    public AlunoDto buscarPorUsuarioId(Long usuarioId) {
        Aluno aluno = alunoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado para o usuário com id: " + usuarioId));
        return toDto(aluno);
    }

    public AlunoDto buscarPorEmail(String email) {
        Aluno aluno = alunoRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com email: " + email));
        return toDto(aluno);
    }

    public AlunoDto buscarPorMatricula(String matricula) {
        Aluno aluno = alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com matrícula: " + matricula));
        return toDto(aluno);
    }

    // LISTAS FILTRADAS
    public List<AlunoDto> listarAlunosAtivos() {
        return alunoRepository.findByStatusAtivoTrue()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosInativos() {
        return alunoRepository.findByStatusAtivoFalse()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosComPlanoNutricional() {
        return alunoRepository.findByPlanoNutricionalAtivoTrue()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosSemPlanoNutricional() {
        return alunoRepository.findByPlanoNutricionalAtivoFalse()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosPorNutricionista(Long nutricionistaId) {
        return alunoRepository.findByNutricionistaId(nutricionistaId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosPorProfessor(Long professorId) {
        return alunoRepository.findByProfessorId(professorId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosPorObjetivo(String objetivo) {
        return alunoRepository.findByObjetivoContainingIgnoreCase(objetivo)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosSemNutricionista() {
        return alunoRepository.findAlunosSemNutricionista()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosSemProfessor() {
        return alunoRepository.findAlunosSemProfessor()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosAtivosComPlanoNutricional() {
        return alunoRepository.findAlunosAtivosComPlanoNutricional()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosPorFaixaEtaria(Integer idadeMin, Integer idadeMax) {
        return alunoRepository.findByIdadeBetween(idadeMin, idadeMax)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosPorNome(String nome) {
        return alunoRepository.findByUsuarioNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlunoDto> listarAlunosRecentes() {
        return alunoRepository.findTop10ByOrderByIdDesc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ATUALIZAÇÕES
    public AlunoDto atualizarAluno(Long id, AlunoDto alunoDto) {
        Aluno existente = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));

        if (alunoDto.getIdade() != null) existente.setIdade(alunoDto.getIdade());
        if (alunoDto.getPeso() != null) existente.setPeso(alunoDto.getPeso());
        if (alunoDto.getAltura() != null) existente.setAltura(alunoDto.getAltura());
        if (alunoDto.getTelefone() != null) existente.setTelefone(alunoDto.getTelefone());
        if (alunoDto.getObjetivo() != null) existente.setObjetivo(alunoDto.getObjetivo());
        if (alunoDto.getMatricula() != null) existente.setMatricula(alunoDto.getMatricula());

        existente.setStatusAtivo(alunoDto.isStatusAtivo());
        existente.setPlanoNutricionalAtivo(alunoDto.isPlanoNutricionalAtivo());

        Aluno alunoAtualizado = alunoRepository.save(existente);
        return toDto(alunoAtualizado);
    }

    public AlunoDto atualizarDadosBasicos(Long id, Integer idade, Double peso, Double altura, String objetivo, String telefone) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));

        if (idade != null) aluno.setIdade(idade);
        if (peso != null) aluno.setPeso(peso);
        if (altura != null) aluno.setAltura(altura);
        if (objetivo != null) aluno.setObjetivo(objetivo);
        if (telefone != null) aluno.setTelefone(telefone);

        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDto(alunoAtualizado);
    }

    public AlunoDto atualizarMedidas(Long id, Double peso, Double altura) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));

        if (peso != null) aluno.setPeso(peso);
        if (altura != null) aluno.setAltura(altura);

        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDto(alunoAtualizado);
    }

    // STATUS E PLANOS
    public AlunoDto ativarAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
        aluno.setStatusAtivo(true);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDto(alunoAtualizado);
    }

    public AlunoDto desativarAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
        aluno.setStatusAtivo(false);
        aluno.setPlanoNutricionalAtivo(false);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDto(alunoAtualizado);
    }

    public AlunoDto ativarPlanoNutricional(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
        if (!aluno.isStatusAtivo()) {
            throw new RuntimeException("Não é possível ativar plano nutricional para aluno inativo.");
        }
        aluno.setPlanoNutricionalAtivo(true);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDto(alunoAtualizado);
    }

    public AlunoDto desativarPlanoNutricional(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
        aluno.setPlanoNutricionalAtivo(false);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDto(alunoAtualizado);
    }

    // VINCULAR PROFISSIONAIS
    public AlunoDto vincularNutricionista(Long alunoId, Long nutricionistaId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + alunoId));

        Nutricionista nutricionista = nutricionistaRepository.findById(nutricionistaId)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com id: " + nutricionistaId));

        aluno.setNutricionista(nutricionista);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDto(alunoAtualizado);
    }

    public AlunoDto vincularProfessor(Long alunoId, Long professorId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + alunoId));

        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + professorId));

        aluno.setProfessor(professor);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDto(alunoAtualizado);
    }

    public AlunoDto desvincularNutricionista(Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + alunoId));

        aluno.setNutricionista(null);
        aluno.setPlanoNutricionalAtivo(false);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDto(alunoAtualizado);
    }

    public AlunoDto desvincularProfessor(Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + alunoId));

        aluno.setProfessor(null);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDto(alunoAtualizado);
    }

    // EXCLUSÃO
    public void deletarAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
        aluno.setStatusAtivo(false);
        aluno.setPlanoNutricionalAtivo(false);
        alunoRepository.save(aluno);
    }

    public void deletarAlunoPermanentemente(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado para exclusão.");
        }
        alunoRepository.deleteById(id);
    }

    // ESTATÍSTICAS
    public long contarAlunosAtivos() {
        return alunoRepository.countByStatusAtivoTrue();
    }

    public long contarAlunosInativos() {
        return alunoRepository.countByStatusAtivoFalse();
    }

    public long contarAlunosComPlanoNutricional() {
        return alunoRepository.countByPlanoNutricionalAtivoTrue();
    }

    public long contarAlunosSemPlanoNutricional() {
        return alunoRepository.countByPlanoNutricionalAtivoFalse();
    }

    public long contarAlunosPorNutricionista(Long nutricionistaId) {
        return alunoRepository.countByNutricionistaId(nutricionistaId);
    }

    public long contarAlunosPorProfessor(Long professorId) {
        return alunoRepository.countByProfessorId(professorId);
    }

    public long contarTotalAlunos() {
        return alunoRepository.count();
    }

    // BUSCA AVANÇADA
    public List<AlunoDto> buscarComFiltros(Boolean statusAtivo, Boolean planoNutricionalAtivo, Long nutricionistaId, Long professorId) {
        return alunoRepository.findWithFilters(statusAtivo, planoNutricionalAtivo, nutricionistaId, professorId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // VALIDAÇÕES
    public boolean existeAlunoPorUsuarioId(Long usuarioId) {
        return alunoRepository.existsByUsuarioId(usuarioId);
    }

    public boolean existeAlunoPorEmail(String email) {
        return alunoRepository.existsByUsuarioEmail(email);
    }

    public boolean existeAlunoPorMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula).isPresent();
    }

    public boolean isAlunoAtivo(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
        return aluno.isStatusAtivo();
    }

    public boolean isPlanoNutricionalAtivo(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
        return aluno.isPlanoNutricionalAtivo();
    }
}