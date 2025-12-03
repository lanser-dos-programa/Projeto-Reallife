package com.reallife.tcc.controller;

import com.reallife.tcc.entity.Exercicio;
import com.reallife.tcc.service.ExercicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercicios")
@RequiredArgsConstructor
public class ExercicioController {

    private final ExercicioService exercicioService;

    @GetMapping
    public ResponseEntity<List<Exercicio>> listar() {
        return ResponseEntity.ok(exercicioService.listarExercicios());
    }
}
