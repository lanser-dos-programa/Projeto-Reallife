package com.reallife.tcc.controller;

import com.reallife.tcc.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.reallife.tcc.service.UsuarioService;
import com.reallife.tcc.security.Role;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // 🔹 Registrar usuário (qualquer um pode acessar)
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario novo = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(novo);
    }

    // 🔹 Login usuário
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
        Usuario autenticado = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());
        return ResponseEntity.ok(autenticado);
    }

    // 🔹 Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // 🔹 Listar todos os usuários (pode proteger depois)
    @GetMapping("/")
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // 🔹 Alterar role do usuário (ex: só admin pode usar)
    @PutMapping("/{id}/role")
    public ResponseEntity<Usuario> alterarRole(@PathVariable Long id, @RequestParam Role role) {
        Usuario atualizado = usuarioService.definirRole(id, role);
        return ResponseEntity.ok(atualizado);
    }
}
