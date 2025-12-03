package com.reallife.tcc.service;

import com.reallife.tcc.dto.ProfessorDto;
import com.reallife.tcc.entity.Professor;
import com.reallife.tcc.entity.Usuario;
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
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlunoService alunoService;

    // CONVERSÕES DTO/ENTITY
    public ProfessorDto toDto(Professor professor) {
        return ProfessorDto.builder()
                .id(professor.getId())
                .formacao(professor.getFormacao())
                .registroProfissional(professor.getRegistroProfissional())
                .especialidade(professor.getEspecialidade())
                .telefone(professor.getTelefone())
                .anosExperiencia(professor.getAnosExperiencia())
                .nome(professor.getUsuario().getNome())
                .email(professor.getUsuario().getEmail())
                .cpf(professor.getUsuario().getCpf())
                .usuarioId(professor.getUsuario().getId())
                .totalAlunos(alunoService.contarAlunosPorProfessor(professor.getId()))
                .build();
    }

    public Professor toEntity(ProfessorDto professorDto) {
        return Professor.builder()
                .id(professorDto.getId())
                .formacao(professorDto.getFormacao())
                .registroProfissional(professorDto.getRegistroProfissional())
                .especialidade(professorDto.getEspecialidade())
                .telefone(professorDto.getTelefone())
                .anosExperiencia(professorDto.getAnosExperiencia())
                .ativo(professorDto.isAtivo())
                .build();
    }

    // CADASTRO
    public ProfessorDto cadastrarProfessor(ProfessorDto professorDto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + usuarioId));

        if (professorRepository.existsByUsuarioId(usuarioId)) {
            throw new RuntimeException("Este usuário já está vinculado a um professor.");
        }

        Professor professor = toEntity(professorDto);
        professor.setUsuario(usuario);
        Professor professorSalvo = professorRepository.save(professor);
        return toDto(professorSalvo);
    }

    // CONSULTAS
    public List<ProfessorDto> listarProfessores() {
        return professorRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProfessorDto buscarPorId(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + id));
        return toDto(professor);
    }

    public Professor buscarEntidadePorId(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + id));
    }

    public ProfessorDto buscarPorUsuarioId(Long usuarioId) {
        Professor professor = professorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado para o usuário com id: " + usuarioId));
        return toDto(professor);
    }

    public ProfessorDto buscarPorEmail(String email) {
        Professor professor = professorRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com email: " + email));
        return toDto(professor);
    }

    // ATUALIZAÇÕES
    public ProfessorDto atualizarProfessor(Long id, ProfessorDto professorDto) {
        Professor existente = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + id));

        if (professorDto.getFormacao() != null) existente.setFormacao(professorDto.getFormacao());
        if (professorDto.getEspecialidade() != null) existente.setEspecialidade(professorDto.getEspecialidade());
        if (professorDto.getTelefone() != null) existente.setTelefone(professorDto.getTelefone());
        if (professorDto.getAnosExperiencia() != null) existente.setAnosExperiencia(professorDto.getAnosExperiencia());

        existente.setAtivo(professorDto.isAtivo());

        Professor professorAtualizado = professorRepository.save(existente);
        return toDto(professorAtualizado);
    }

    // STATUS
    public ProfessorDto ativarProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + id));
        professor.setAtivo(true);
        Professor professorAtualizado = professorRepository.save(professor);
        return toDto(professorAtualizado);
    }

    public ProfessorDto desativarProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + id));
        professor.setAtivo(false);
        Professor professorAtualizado = professorRepository.save(professor);
        return toDto(professorAtualizado);
    }

    // EXCLUSÃO
    public void deletarProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com id: " + id));
        professor.setAtivo(false);
        professorRepository.save(professor);
    }

    // ESTATÍSTICAS
    public long contarProfessoresAtivos() {
        return professorRepository.countByAtivoTrue();
    }

    public long contarTotalProfessores() {
        return professorRepository.count();
    }

    // VALIDAÇÕES
    public boolean existeProfessorPorUsuarioId(Long usuarioId) {
        return professorRepository.existsByUsuarioId(usuarioId);
    }

    public boolean existeProfessorPorEmail(String email) {
        return professorRepository.existsByUsuarioEmail(email);
    }
}