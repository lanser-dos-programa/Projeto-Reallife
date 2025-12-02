import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form: FormGroup;
  loading = false;

  constructor(private fb: FormBuilder, private router: Router) {
    this.form = this.fb.group({
      email: ['', [Validators.required]],
      senha: ['', Validators.required]
    });
  }

  // -------------------------------------
  // 🔥 SUBMIT DO LOGIN
  // -------------------------------------
  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;

    const { email, senha } = this.form.value;

    fetch("http://localhost:8080/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, senha })
    })
      .then(async res => {
        if (!res.ok) throw new Error("Credenciais inválidas");
        return await res.json();
      })
      .then(usuario => {

        // Garantia de retorno correto
        if (!usuario || !usuario.tipo) {
          alert("Usuário ou senha inválidos");
          return;
        }

        console.log("Usuário logado:", usuario);

        // -------------------------------
        // 🔥 REDIRECIONAMENTO POR FUNÇÃO
        // -------------------------------
        switch (usuario.tipo.toUpperCase()) {

          case "PROFESSOR":
            this.router.navigate(["/dashboard"]);
            break;

          case "ALUNO":
            this.router.navigate(["/home"], {
              queryParams: { id: usuario.id, nome: usuario.nome }
            });
            break;

          case "NUTRICIONISTA":
            this.router.navigate(["/escolher-aluno"], {
              queryParams: { id: usuario.id, nome: usuario.nome }
            });
            break;

          default:
            alert("Tipo de usuário desconhecido");
        }
      })
      .catch((err) => {
        console.error(err);
        alert("Erro ao conectar ao servidor");
      })
      .finally(() => {
        this.loading = false;
      });
  }

  // -------------------------------------
  //  LOGIN COM GOOGLE (placeholder)
  // -------------------------------------
  loginGoogle() {
    alert("Login com Google ainda não implementado");
  }
}
