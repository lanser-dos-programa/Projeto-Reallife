package com.reallife.tcc.service;

import com.reallife.tcc.entity.Aluno;
import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.repository.AlunoRepository;
import com.reallife.tcc.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final UsuarioRepository usuarioRepository;

    // CADASTRO
    public Aluno cadastrarAluno(Aluno aluno, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + usuarioId));

        if (alunoRepository.existsByUsuarioId(usuarioId)) {
            throw new RuntimeException("Este usuário já está vinculado a um aluno.");
        }

        aluno.setUsuario(usuario);
        return alunoRepository.save(aluno);
    }

    public Aluno cadastrarAlunoComUsuario(Aluno aluno, Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Já existe um usuário com este email: " + usuario.getEmail());
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        aluno.setUsuario(usuarioSalvo);
        return alunoRepository.save(aluno);
    }

    // CONSULTAS
    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));
    }

    public Aluno buscarPorUsuarioId(Long usuarioId) {
        return alunoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado para o usuário com id: " + usuarioId));
    }

    public Aluno buscarPorEmail(String email) {
        return alunoRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com email: " + email));
    }

    public Aluno buscarPorMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com matrícula: " + matricula));
    }

    // LISTAS FILTRADAS
    public List<Aluno> listarAlunosAtivos() {
        return alunoRepository.findByStatusAtivoTrue();
    }

    public List<Aluno> listarAlunosInativos() {
        return alunoRepository.findByStatusAtivoFalse();
    }

    public List<Aluno> listarAlunosComPlanoNutricional() {
        return alunoRepository.findByPlanoNutricionalAtivoTrue();
    }

    public List<Aluno> listarAlunosSemPlanoNutricional() {
        return alunoRepository.findByPlanoNutricionalAtivoFalse();
    }

    public List<Aluno> listarAlunosPorNutricionista(Long nutricionistaId) {
        return alunoRepository.findByNutricionistaId(nutricionistaId);
    }

    public List<Aluno> listarAlunosPorProfessor(Long professorId) {
        return alunoRepository.findByProfessorId(professorId);
    }

    public List<Aluno> listarAlunosPorObjetivo(String objetivo) {
        return alunoRepository.findByObjetivoContainingIgnoreCase(objetivo);
    }

    public List<Aluno> listarAlunosSemNutricionista() {
        return alunoRepository.findAlunosSemNutricionista();
    }

    public List<Aluno> listarAlunosSemProfessor() {
        return alunoRepository.findAlunosSemProfessor();
    }

    public List<Aluno> listarAlunosAtivosComPlanoNutricional() {
        return alunoRepository.findAlunosAtivosComPlanoNutricional();
    }

    public List<Aluno> listarAlunosPorFaixaEtaria(Integer idadeMin, Integer idadeMax) {
        return alunoRepository.findByIdadeBetween(idadeMin, idadeMax);
    }

    public List<Aluno> listarAlunosPorNome(String nome) {
        return alunoRepository.findByUsuarioNomeContainingIgnoreCase(nome);
    }

    public List<Aluno> listarAlunosRecentes() {
        return alunoRepository.findTop10ByOrderByIdDesc();
    }

    // ATUALIZAÇÕES
    public Aluno atualizarAluno(Long id, Aluno dadosAtualizados) {
        Aluno existente = buscarPorId(id);

        // Atualiza apenas campos não nulos
        if (dadosAtualizados.getIdade() != null) {
            existente.setIdade(dadosAtualizados.getIdade());
        }
        if (dadosAtualizados.getPeso() != null) {
            existente.setPeso(dadosAtualizados.getPeso());
        }
        if (dadosAtualizados.getAltura() != null) {
            existente.setAltura(dadosAtualizados.getAltura());
        }
        if (dadosAtualizados.getTelefone() != null) {
            existente.setTelefone(dadosAtualizados.getTelefone());
        }
        if (dadosAtualizados.getObjetivo() != null) {
            existente.setObjetivo(dadosAtualizados.getObjetivo());
        }
        if (dadosAtualizados.getMatricula() != null) {
            existente.setMatricula(dadosAtualizados.getMatricula());
        }

        existente.setStatusAtivo(dadosAtualizados.isStatusAtivo());
        existente.setPlanoNutricionalAtivo(dadosAtualizados.isPlanoNutricionalAtivo());

        if (dadosAtualizados.getNutricionista() != null) {
            existente.setNutricionista(dadosAtualizados.getNutricionista());
        }
        if (dadosAtualizados.getProfessor() != null) {
            existente.setProfessor(dadosAtualizados.getProfessor());
        }

        return alunoRepository.save(existente);
    }

    public Aluno atualizarDadosBasicos(Long id, Integer idade, Double peso, Double altura, String objetivo, String telefone) {
        Aluno aluno = buscarPorId(id);

        if (idade != null) aluno.setIdade(idade);
        if (peso != null) aluno.setPeso(peso);
        if (altura != null) aluno.setAltura(altura);
        if (objetivo != null) aluno.setObjetivo(objetivo);
        if (telefone != null) aluno.setTelefone(telefone);

        return alunoRepository.save(aluno);
    }

    public Aluno atualizarMedidas(Long id, Double peso, Double altura) {
        Aluno aluno = buscarPorId(id);

        if (peso != null) aluno.setPeso(peso);
        if (altura != null) aluno.setAltura(altura);

        return alunoRepository.save(aluno);
    }

    // STATUS E PLANOS
    public Aluno ativarAluno(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setStatusAtivo(true);
        return alunoRepository.save(aluno);
    }

    public Aluno desativarAluno(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setStatusAtivo(false);
        aluno.setPlanoNutricionalAtivo(false); // Desativa plano também
        return alunoRepository.save(aluno);
    }

    public Aluno ativarPlanoNutricional(Long id) {
        Aluno aluno = buscarPorId(id);
        if (!aluno.isStatusAtivo()) {
            throw new RuntimeException("Não é possível ativar plano nutricional para aluno inativo.");
        }
        aluno.setPlanoNutricionalAtivo(true);
        return alunoRepository.save(aluno);
    }

    public Aluno desativarPlanoNutricional(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setPlanoNutricionalAtivo(false);
        return alunoRepository.save(aluno);
    }

    // VINCULAR PROFISSIONAIS
    public Aluno vincularNutricionista(Long alunoId, Long nutricionistaId) {
        Aluno aluno = buscarPorId(alunoId);
        // Implemente a busca do nutricionista aqui
        // Nutricionista nutricionista = nutricionistaService.buscarPorId(nutricionistaId);
        // aluno.setNutricionista(nutricionista);
        return alunoRepository.save(aluno);
    }

    public Aluno vincularProfessor(Long alunoId, Long professorId) {
        Aluno aluno = buscarPorId(alunoId);
        // Implemente a busca do professor aqui
        // Professor professor = professorService.buscarPorId(professorId);
        // aluno.setProfessor(professor);
        return alunoRepository.save(aluno);
    }

    public Aluno desvincularNutricionista(Long alunoId) {
        Aluno aluno = buscarPorId(alunoId);
        aluno.setNutricionista(null);
        aluno.setPlanoNutricionalAtivo(false); // Desativa plano ao desvincular
        return alunoRepository.save(aluno);
    }

    public Aluno desvincularProfessor(Long alunoId) {
        Aluno aluno = buscarPorId(alunoId);
        aluno.setProfessor(null);
        return alunoRepository.save(aluno);
    }

    // EXCLUSÃO
    public void deletarAluno(Long id) {
        Aluno aluno = buscarPorId(id);
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

    // ESTATÍSTICAS E RELATÓRIOS
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
    public List<Aluno> buscarComFiltros(Boolean statusAtivo, Boolean planoNutricionalAtivo,
                                        Long nutricionistaId, Long professorId) {
        return alunoRepository.findWithFilters(statusAtivo, planoNutricionalAtivo, nutricionistaId, professorId);
    }

    public List<Aluno> buscarPorFaixaDePeso(Double pesoMin, Double pesoMax) {
        return alunoRepository.findByPesoBetween(pesoMin, pesoMax);
    }

    public List<Aluno> buscarPorFaixaDeAltura(Double alturaMin, Double alturaMax) {
        return alunoRepository.findByAlturaBetween(alturaMin, alturaMax);
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

    // VERIFICAÇÕES DE STATUS
    public boolean isAlunoAtivo(Long id) {
        Aluno aluno = buscarPorId(id);
        return aluno.isStatusAtivo();
    }

    public boolean isPlanoNutricionalAtivo(Long id) {
        Aluno aluno = buscarPorId(id);
        return aluno.isPlanoNutricionalAtivo();
    }

    public boolean temNutricionista(Long id) {
        Aluno aluno = buscarPorId(id);
        return aluno.getNutricionista() != null;
    }

    public boolean temProfessor(Long id) {
        Aluno aluno = buscarPorId(id);
        return aluno.getProfessor() != null;
    }
}