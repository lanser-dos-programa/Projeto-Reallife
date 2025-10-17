import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router'; // ✅ Import necessário para navegação

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) { // ✅ injeta o Router
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      const { email, senha } = this.form.value;
      console.log('Tentando login:', email, senha);

      //  Aqui futuramente  vai validar o login com o backend
      // Se o login for bem-sucedido:
      this.router.navigate(['/home']); // redireciona para a página Home
    } else {
      this.form.markAllAsTouched();
    }
  }

  loginGoogle() {
    console.log('Login com Google ainda não implementado');
  }
}
