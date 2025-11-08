package com.reallife.tcc.controller;

import com.reallife.tcc.entity.Recepcao;
import com.reallife.tcc.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.reallife.tcc.security.Role;
import com.reallife.tcc.service.RecepcaoService;
import com.reallife.tcc.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/recepcao")
@RequiredArgsConstructor
public class RecepcaoController {

    private final RecepcaoService recepcaoService;
    private final UsuarioService usuarioService;


    // CRUD Recepcionista

    // Cadastrar um novo funcionário da recepção
    @PostMapping
    public ResponseEntity<Recepcao> cadastrar(@RequestBody Recepcao recepcao) {
        Recepcao novo = recepcaoService.cadastrarRecepcionista(recepcao);
        return ResponseEntity.ok(novo);
    }

    // Listar todos os funcionários da recepção
    @GetMapping
    public ResponseEntity<List<Recepcao>> listarTodos() {
        List<Recepcao> lista = recepcaoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // Buscar funcionário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Recepcao> buscarPorId(@PathVariable Long id) {
        Recepcao recepcionista = recepcaoService.buscarPorId(id);
        return ResponseEntity.ok(recepcionista);
    }

    // Atualizar funcionário
    @PutMapping("/{id}")
    public ResponseEntity<Recepcao> atualizar(@PathVariable Long id, @RequestBody Recepcao dadosAtualizados) {
        Recepcao atualizado = recepcaoService.atualizar(id, dadosAtualizados);
        return ResponseEntity.ok(atualizado);
    }

    // Deletar funcionário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        recepcaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Login simples exemplo básico, sem JWT ainda
    @PostMapping("/login")
    public ResponseEntity<Recepcao> login(@RequestParam String email, @RequestParam String senha) {
        Recepcao recepcionista = recepcaoService.login(email, senha);
        return ResponseEntity.ok(recepcionista);
    }

    // Alterar o cargo role de usuários
    @PutMapping("/definir-role/{id}")
    public ResponseEntity<Usuario> definirRole(
            @PathVariable Long id,
            @RequestParam Role role
    ) {
        Usuario atualizado = usuarioService.definirRole(id, role);
        return ResponseEntity.ok(atualizado);
    }
}
