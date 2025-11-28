package com.reallife.tcc.controller;

import com.reallife.tcc.dto.FichaDto;
import com.reallife.tcc.entity.FichaDeTreino;
import com.reallife.tcc.service.FichaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor/fichas")
@RequiredArgsConstructor
public class ProfessorController {

    private final FichaService fichaService;

    @PostMapping
    public ResponseEntity<FichaDeTreino> criarFicha(@RequestBody FichaDto dto) {
        return ResponseEntity.ok(fichaService.criarFicha(dto));
    }
}
