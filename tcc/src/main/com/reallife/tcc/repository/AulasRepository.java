package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Aulas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AulasRepository extends JpaRepository<Aulas, Long> {

    // Buscar por professor
    List<Aulas> findByProfessorId(Long professorId);

    // Buscar por data
    List<Aulas> findByData(LocalDate data);

    // Buscar por período
    List<Aulas> findByDataBetween(LocalDate inicio, LocalDate fim);

    // Buscar ativas
    List<Aulas> findByAtivaTrue();

    // Buscar inativas
    List<Aulas> findByAtivaFalse();

    // Buscar por nome (contém)
    List<Aulas> findByNomeContainingIgnoreCase(String nome);

    // Buscar próximas aulas (hoje em diante, ativas)
    @Query("SELECT a FROM Aulas a WHERE a.data >= :hoje AND a.ativa = true ORDER BY a.data, a.horaInicio")
    List<Aulas> findProximasAulas(@Param("hoje") LocalDate hoje);

    // Buscar por local
    List<Aulas> findByLocal(String local);

    // Verificar se existe aula no mesmo horário/local
    @Query("SELECT COUNT(a) > 0 FROM Aulas a WHERE a.data = :data AND a.horaInicio = :horaInicio AND a.local = :local")
    boolean existsByDataAndHoraInicioAndLocal(@Param("data") LocalDate data,
                                              @Param("horaInicio") LocalTime horaInicio,
                                              @Param("local") String local);

    // Buscar aulas do dia atual
    @Query("SELECT a FROM Aulas a WHERE a.data = CURRENT_DATE AND a.ativa = true")
    List<Aulas> findAulasHoje();

    // Buscar aulas da semana
    @Query("SELECT a FROM Aulas a WHERE a.data BETWEEN :segunda AND :domingo AND a.ativa = true ORDER BY a.data, a.horaInicio")
    List<Aulas> findAulasSemana(@Param("segunda") LocalDate segunda,
                                @Param("domingo") LocalDate domingo);

    // Contar aulas por professor
    @Query("SELECT COUNT(a) FROM Aulas a WHERE a.professor.id = :professorId")
    long countByProfessorId(@Param("professorId") Long professorId);

    // Buscar por professor e data
    List<Aulas> findByProfessorIdAndData(Long professorId, LocalDate data);

    // Buscar por data, hora e local
    List<Aulas> findByDataAndHoraInicioAndLocal(LocalDate data, LocalTime horaInicio, String local);

    // Buscar próximas aulas filtradas por nome
    @Query("SELECT a FROM Aulas a WHERE a.data >= :hoje AND a.ativa = true " +
            "AND (:nome IS NULL OR LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
            "ORDER BY a.data, a.horaInicio")
    List<Aulas> findProximasAulasPorNome(@Param("hoje") LocalDate hoje,
                                         @Param("nome") String nome);
}