package com.reallife.tcc.controller;

import com.reallife.tcc.entity.Usuario;
import com.reallife.tcc.security.Role;
import com.reallife.tcc.service.AuthService;
import com.reallife.tcc.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthService authService;


    //  CADASTRAR USUÁRIO
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novo = usuarioService.criar(usuario);
        return ResponseEntity.ok(novo);
    }


    //  LOGIN
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestParam String email,
            @RequestParam String senha
    ) {
        String token = authService.login(email, senha);
        return ResponseEntity.ok(Map.of("token", token));
    }


    //  BUSCA COM FILTROS (NOVO ENDPOINT)
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarUsuario(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Role role) {
        List<Usuario> usuarios = usuarioService.buscarUsuariosComFiltros(nome, email, role);
        return ResponseEntity.ok(usuarios);
    }


    //  LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }


    //  BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }


    //  ATUALIZAR DADOS
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuarioAtualizado
    ) {
        Usuario atualizado = usuarioService.atualizar(id, usuarioAtualizado);
        return ResponseEntity.ok(atualizado);
    }


    //  ALTERAR SENHA
    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> alterarSenha(
            @PathVariable Long id,
            @RequestParam String senhaAtual,
            @RequestParam String novaSenha
    ) {
        usuarioService.alterarSenha(id, senhaAtual, novaSenha);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/role")
    public ResponseEntity<Usuario> definirRole(
            @PathVariable Long id,
            @RequestParam Role role
    ) {
        Usuario atualizado = usuarioService.definirRole(id, role);
        return ResponseEntity.ok(atualizado);
    }


    //  ATIVAR / DESATIVAR USUÁRIO
    @PutMapping("/{id}/status")
    public ResponseEntity<Usuario> alterarStatus(
            @PathVariable Long id,
            @RequestParam boolean ativo
    ) {
        Usuario atualizado = usuarioService.alterarStatus(id, ativo);
        return ResponseEntity.ok(atualizado);
    }


    //  DELETAR USUÁRIO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}