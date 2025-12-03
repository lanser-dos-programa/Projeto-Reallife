package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Alimentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimentos, Long> {

    // ========== BUSCAS POR NOME ==========
    Optional<Alimentos> findByNome(String nome);
    List<Alimentos> findByNomeContainingIgnoreCase(String nome);
    List<Alimentos> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);

    // ========== BUSCAS POR CATEGORIA ==========
    List<Alimentos> findByCategoria(String categoria);
    List<Alimentos> findByCategoriaContainingIgnoreCase(String categoria);
    List<Alimentos> findByCategoriaAndAtivoTrue(String categoria);
    List<Alimentos> findByCategoriaAndCaloriasLessThanAndAtivoTrue(String categoria, double calorias);

    // ========== BUSCAS POR STATUS ==========
    List<Alimentos> findByAtivoTrue();
    List<Alimentos> findByAtivoFalse();

    // ========== BUSCAS POR NUTRICIONISTA ==========
    List<Alimentos> findByNutricionistaId(Long nutricionistaId);
    List<Alimentos> findByNutricionistaIdAndAtivoTrue(Long nutricionistaId);

    // ========== BUSCAS POR VALORES NUTRICIONAIS ==========
    List<Alimentos> findByCaloriasLessThanAndAtivoTrue(double calorias);
    List<Alimentos> findByProteinasGreaterThanAndAtivoTrue(double proteinas);

    @Query("SELECT a FROM Alimentos a WHERE a.calorias BETWEEN :calMin AND :calMax")
    List<Alimentos> findByCaloriasBetween(@Param("calMin") Double calMin, @Param("calMax") Double calMax);

    @Query("SELECT a FROM Alimentos a WHERE a.proteinas BETWEEN :protMin AND :protMax")
    List<Alimentos> findByProteinasBetween(@Param("protMin") Double protMin, @Param("protMax") Double protMax);

    @Query("SELECT a FROM Alimentos a WHERE a.carboidratos BETWEEN :carbMin AND :carbMax")
    List<Alimentos> findByCarboidratosBetween(@Param("carbMin") Double carbMin, @Param("carbMax") Double carbMax);

    @Query("SELECT a FROM Alimentos a WHERE a.gorduras BETWEEN :gordMin AND :gordMax")
    List<Alimentos> findByGordurasBetween(@Param("gordMin") Double gordMin, @Param("gordMax") Double gordMax);

    // ========== CONSULTAS ESPECIAIS ==========
    @Query("SELECT DISTINCT a.categoria FROM Alimentos a WHERE a.ativo = true")
    List<String> findDistinctCategorias();

    @Query("SELECT a FROM Alimentos a ORDER BY a.nome ASC")
    List<Alimentos> findAllOrderByNome();

    @Query(value = "SELECT * FROM alimentos WHERE ativo = true ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Alimentos> findTop10Recentes();

    // ========== CONTAGENS ==========
    long countByAtivoTrue();
    long countByAtivoFalse();
    long countByNutricionistaId(Long nutricionistaId);

    // ========== MÉTODOS PARA AUTOMAÇÃO ==========
    @Query("SELECT a FROM Alimentos a WHERE a.ativo = true AND a.calorias < :maxCalorias ORDER BY a.proteinas DESC")
    List<Alimentos> findAlimentosParaEmagrecimento(@Param("maxCalorias") double maxCalorias);

    @Query("SELECT a FROM Alimentos a WHERE a.ativo = true AND a.proteinas > :minProteinas ORDER BY a.calorias DESC")
    List<Alimentos> findAlimentosParaGanhoMassa(@Param("minProteinas") double minProteinas);

    @Query("SELECT a FROM Alimentos a WHERE a.ativo = true AND a.carboidratos < :maxCarboidratos ORDER BY a.proteinas DESC")
    List<Alimentos> findAlimentosLowCarb(@Param("maxCarboidratos") double maxCarboidratos);

    @Query("SELECT a FROM Alimentos a WHERE a.ativo = true AND a.gorduras < :maxGorduras ORDER BY a.calorias ASC")
    List<Alimentos> findAlimentosLowFat(@Param("maxGorduras") double maxGorduras);
}