package com.reallife.tcc.service;

import com.reallife.tcc.dto.AlimentoDto;
import com.reallife.tcc.entity.Alimentos;
import com.reallife.tcc.repository.AlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlimentosImportService {

    private final AlimentoRepository alimentoRepository;


    private AlimentoDto toDto(Alimentos alimento) {
        if (alimento == null) {
            return null;
        }

        return AlimentoDto.builder()
                .id(alimento.getId())
                .nome(alimento.getNome())
                .calorias(alimento.getCalorias())
                .proteinas(alimento.getProteinas())
                .carboidratos(alimento.getCarboidratos())
                .gorduras(alimento.getGorduras())
                .categoria(alimento.getCategoria())
                .medidaCaseira(alimento.getMedidaCaseira())
                .ativo(alimento.isAtivo())
                .nutricionistaId(alimento.getNutricionista() != null ? alimento.getNutricionista().getId() : null)
                .nutricionistaNome(alimento.getNutricionista() != null ? alimento.getNutricionista().getNome() : null)
                .build();
    }


    public List<AlimentoDto> sugerirAlimentosPorCategoria(String categoria, String objetivo) {
        List<Alimentos> alimentos;

        switch (objetivo.toUpperCase()) {
            case "EMAGRECER":
                alimentos = alimentoRepository.findByCategoriaAndCaloriasLessThanAndAtivoTrue(categoria, 150.0);
                break;
            case "GANHAR_MASSA":
                alimentos = alimentoRepository.findByCategoriaAndAtivoTrue(categoria)
                        .stream()
                        .filter(a -> a.getProteinas() > 10)
                        .collect(Collectors.toList());
                break;
            case "LOW_CARB":
                alimentos = alimentoRepository.findAlimentosLowCarb(20.0);
                break;
            default:
                alimentos = alimentoRepository.findByCategoriaAndAtivoTrue(categoria);
        }

        return alimentos.stream().map(this::toDto).collect(Collectors.toList());
    }


    public Map<String, Double> calcularNutrientesAutomaticamente(List<Long> alimentosIds) {
        List<Alimentos> alimentos = alimentoRepository.findAllById(alimentosIds);

        Map<String, Double> nutrientes = new HashMap<>();
        nutrientes.put("calorias", alimentos.stream().mapToDouble(Alimentos::getCalorias).sum());
        nutrientes.put("proteinas", alimentos.stream().mapToDouble(Alimentos::getProteinas).sum());
        nutrientes.put("carboidratos", alimentos.stream().mapToDouble(Alimentos::getCarboidratos).sum());
        nutrientes.put("gorduras", alimentos.stream().mapToDouble(Alimentos::getGorduras).sum());

        return nutrientes;
    }


    public List<AlimentoDto> sugerirCombinacao(double caloriasDesejadas, String restricoes) {
        List<Alimentos> alimentos;

        if (restricoes != null && restricoes.contains("LOW_CARB")) {
            alimentos = alimentoRepository.findAlimentosLowCarb(30.0);
        } else if (restricoes != null && restricoes.contains("HIGH_PROTEIN")) {
            alimentos = alimentoRepository.findAlimentosParaGanhoMassa(15.0);
        } else {
            alimentos = alimentoRepository.findByCaloriasLessThanAndAtivoTrue(caloriasDesejadas);
        }

        return alimentos.stream()
                .map(this::toDto)
                .limit(8)
                .collect(Collectors.toList());
    }

    public List<AlimentoDto> listarAlimentosPorCategoria(String categoria) {
        return alimentoRepository.findByCategoriaAndAtivoTrue(categoria)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlimentoDto> buscarAlimentosPorNome(String nome) {
        return alimentoRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AlimentoDto> listarTodosAlimentosAtivos() {
        return alimentoRepository.findByAtivoTrue()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<String> listarCategorias() {
        return alimentoRepository.findDistinctCategorias();
    }


    public List<AlimentoDto> alimentosRecentes() {
        return alimentoRepository.findTop10Recentes()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Map<String, Object> estatisticasAlimentos() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalAtivos", alimentoRepository.countByAtivoTrue());
        stats.put("totalInativos", alimentoRepository.countByAtivoFalse());
        stats.put("categorias", alimentoRepository.findDistinctCategorias().size());
        return stats;
    }
}