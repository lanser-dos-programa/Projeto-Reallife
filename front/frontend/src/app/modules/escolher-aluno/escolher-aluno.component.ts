import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

interface Aluno {
  id: number;
  nome: string;
}

@Component({
  selector: 'app-escolher-aluno',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './escolher-aluno.component.html',
  styleUrls: ['./escolher-aluno.component.css']
})
export class EscolherAlunoComponent {

  alunos: Aluno[] = [
    { id: 1, nome: 'João Silva' },
    { id: 2, nome: 'Maria Oliveira' },
    { id: 3, nome: 'Carlos Andrade' },
    { id: 4, nome: 'Ana Santos' }
  ];

  constructor(private router: Router) {}

selecionarAluno(id: number, nome: string) {
  this.router.navigate(['/nutricionista', id, nome]);

  }
}
