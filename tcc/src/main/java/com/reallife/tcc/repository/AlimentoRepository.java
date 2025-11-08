package com.reallife.tcc.repository;

import com.reallife.tcc.entity.Alimentos;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SpringBootApplication
public interface AlimentoRepository extends JpaRepository<Alimentos, Long> {
}
