import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      const { email, senha } = this.form.value;
      console.log('Tentando login:', email, senha);
      // futuramente: chamada ao backend
    } else {
      this.form.markAllAsTouched();
    }
  }

  loginGoogle() {
    console.log('Login com Google ainda não implementado');
  }
}
