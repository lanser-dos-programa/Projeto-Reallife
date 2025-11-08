package com.reallife.tcc.service;

import com.reallife.tcc.dto.NutricionistaDto;
import com.reallife.tcc.entity.Nutricionista;
import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.repository.NutricionistaRepository;
import com.reallife.tcc.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NutricionistaService {

    private final NutricionistaRepository nutricionistaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlunoService alunoService;

    // CONVERSÕES DTO/ENTITY
    public NutricionistaDto toDto(Nutricionista nutricionista) {
        return NutricionistaDto.builder()
                .id(nutricionista.getId())
                .registroProfissional(nutricionista.getRegistroProfissional())
                .formacao(nutricionista.getFormacao())
                .especialidade(nutricionista.getEspecialidade())
                .telefone(nutricionista.getTelefone())
                .experiencia(nutricionista.getExperiencia())
                .ativo(nutricionista.isAtivo())
                .nome(nutricionista.getUsuario().getNome())
                .email(nutricionista.getUsuario().getEmail())
                .cpf(nutricionista.getUsuario().getCpf())
                .usuarioId(nutricionista.getUsuario().getId())
                .totalAlunos(alunoService.contarAlunosPorNutricionista(nutricionista.getId()))
                .build();
    }

    public Nutricionista toEntity(NutricionistaDto nutricionistaDto) {
        return Nutricionista.builder()
                .id(nutricionistaDto.getId())
                .registroProfissional(nutricionistaDto.getRegistroProfissional())
                .formacao(nutricionistaDto.getFormacao())
                .especialidade(nutricionistaDto.getEspecialidade())
                .telefone(nutricionistaDto.getTelefone())
                .experiencia(nutricionistaDto.getExperiencia())
                .ativo(nutricionistaDto.isAtivo())
                .build();
    }

    // CADASTRO
    public NutricionistaDto cadastrarNutricionista(NutricionistaDto nutricionistaDto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + usuarioId));

        if (nutricionistaRepository.existsByUsuarioId(usuarioId)) {
            throw new RuntimeException("Este usuário já está vinculado a um nutricionista.");
        }

        Nutricionista nutricionista = toEntity(nutricionistaDto);
        nutricionista.setUsuario(usuario);
        Nutricionista nutricionistaSalvo = nutricionistaRepository.save(nutricionista);
        return toDto(nutricionistaSalvo);
    }

    // CONSULTAS
    public List<NutricionistaDto> listarNutricionistas() {
        return nutricionistaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public NutricionistaDto buscarPorId(Long id) {
        Nutricionista nutricionista = nutricionistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com id: " + id));
        return toDto(nutricionista);
    }

    public Nutricionista buscarEntidadePorId(Long id) {
        return nutricionistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com id: " + id));
    }

    public NutricionistaDto buscarPorUsuarioId(Long usuarioId) {
        Nutricionista nutricionista = nutricionistaRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado para o usuário com id: " + usuarioId));
        return toDto(nutricionista);
    }

    public NutricionistaDto buscarPorEmail(String email) {
        Nutricionista nutricionista = nutricionistaRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com email: " + email));
        return toDto(nutricionista);
    }

    public NutricionistaDto buscarPorRegistro(String registroProfissional) {
        Nutricionista nutricionista = nutricionistaRepository.findByRegistroProfissional(registroProfissional)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com registro: " + registroProfissional));
        return toDto(nutricionista);
    }

    // ATUALIZAÇÕES
    public NutricionistaDto atualizarNutricionista(Long id, NutricionistaDto nutricionistaDto) {
        Nutricionista existente = nutricionistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com id: " + id));

        if (nutricionistaDto.getFormacao() != null) existente.setFormacao(nutricionistaDto.getFormacao());
        if (nutricionistaDto.getEspecialidade() != null) existente.setEspecialidade(nutricionistaDto.getEspecialidade());
        if (nutricionistaDto.getTelefone() != null) existente.setTelefone(nutricionistaDto.getTelefone());
        if (nutricionistaDto.getExperiencia() != null) existente.setExperiencia(nutricionistaDto.getExperiencia());

        existente.setAtivo(nutricionistaDto.isAtivo());

        Nutricionista nutricionistaAtualizado = nutricionistaRepository.save(existente);
        return toDto(nutricionistaAtualizado);
    }

    // STATUS
    public NutricionistaDto ativarNutricionista(Long id) {
        Nutricionista nutricionista = nutricionistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com id: " + id));
        nutricionista.setAtivo(true);
        Nutricionista nutricionistaAtualizado = nutricionistaRepository.save(nutricionista);
        return toDto(nutricionistaAtualizado);
    }

    public NutricionistaDto desativarNutricionista(Long id) {
        Nutricionista nutricionista = nutricionistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com id: " + id));
        nutricionista.setAtivo(false);
        Nutricionista nutricionistaAtualizado = nutricionistaRepository.save(nutricionista);
        return toDto(nutricionistaAtualizado);
    }

    // EXCLUSÃO
    public void deletarNutricionista(Long id) {
        Nutricionista nutricionista = nutricionistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado com id: " + id));
        nutricionista.setAtivo(false);
        nutricionistaRepository.save(nutricionista);
    }

    // ESTATÍSTICAS
    public long contarNutricionistasAtivos() {
        return nutricionistaRepository.countByAtivoTrue();
    }

    public long contarTotalNutricionistas() {
        return nutricionistaRepository.count();
    }

    // VALIDAÇÕES
    public boolean existeNutricionistaPorUsuarioId(Long usuarioId) {
        return nutricionistaRepository.existsByUsuarioId(usuarioId);
    }

    public boolean existeNutricionistaPorEmail(String email) {
        return nutricionistaRepository.existsByUsuarioEmail(email);
    }

    public boolean existeNutricionistaPorRegistro(String registroProfissional) {
        return nutricionistaRepository.findByRegistroProfissional(registroProfissional).isPresent();
    }
}