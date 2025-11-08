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

    Optional<Alimentos> findByNome(String nome);
    List<Alimentos> findByNomeContainingIgnoreCase(String nome);

    List<Alimentos> findByCategoria(String categoria);
    List<Alimentos> findByCategoriaContainingIgnoreCase(String categoria);

    List<Alimentos> findByAtivoTrue();
    List<Alimentos> findByAtivoFalse();

    List<Alimentos> findByNutricionistaId(Long nutricionistaId);

    @Query("SELECT a FROM Alimentos a WHERE a.calorias BETWEEN :calMin AND :calMax")
    List<Alimentos> findByCaloriasBetween(@Param("calMin") Double calMin, @Param("calMax") Double calMax);

    @Query("SELECT a FROM Alimentos a WHERE a.proteinas BETWEEN :protMin AND :protMax")
    List<Alimentos> findByProteinasBetween(@Param("protMin") Double protMin, @Param("protMax") Double protMax);

    @Query("SELECT a FROM Alimentos a WHERE a.carboidratos BETWEEN :carbMin AND :carbMax")
    List<Alimentos> findByCarboidratosBetween(@Param("carbMin") Double carbMin, @Param("carbMax") Double carbMax);

    @Query("SELECT a FROM Alimentos a WHERE a.gorduras BETWEEN :gordMin AND :gordMax")
    List<Alimentos> findByGordurasBetween(@Param("gordMin") Double gordMin, @Param("gordMax") Double gordMax);

    @Query("SELECT DISTINCT a.categoria FROM Alimentos a WHERE a.ativo = true")
    List<String> findDistinctCategorias();

    long countByAtivoTrue();
    long countByAtivoFalse();
    long countByNutricionistaId(Long nutricionistaId);

    @Query("SELECT a FROM Alimentos a ORDER BY a.nome ASC")
    List<Alimentos> findAllOrderByNome();

    @Query(value = "SELECT * FROM alimentos WHERE ativo = true ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Alimentos> findTop10Recentes();
}