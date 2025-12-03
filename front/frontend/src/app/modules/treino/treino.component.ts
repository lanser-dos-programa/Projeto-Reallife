import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TreinoService } from '../../services/treino.service';

@Component({
  selector: 'app-treino',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './treino.component.html',
  styleUrls: ['./treino.component.css']
})
export class TreinoComponent implements OnInit {

  dias: string[] = [
    'Segunda','Terça','Quarta','Quinta','Sexta','Sábado','Domingo'
  ];

  treinoSelecionado: any[] = [];
  treinosDoAluno: any = {};

  alunoId: number = 0;  // ← Você vai receber esse ID ao logar ou escolher aluno

  constructor(private treinoService: TreinoService) {}

  ngOnInit() {
    // Exemplo: aluno já está logado e o ID foi salvo no localStorage
    this.alunoId = Number(localStorage.getItem("alunoId") || 0);

    this.carregarTreinos();
  }

  carregarTreinos() {
    this.treinoService.getTreinosDoAluno(this.alunoId).subscribe({
      next: (dados) => {
        this.treinosDoAluno = dados;  // formato: { Segunda: [...], Terça: [...], ... }
      },
      error: (err) => console.error(err)
    });
  }

  selecionarDia(dia: string) {
    this.treinoSelecionado = this.treinosDoAluno[dia] || [];
  }

}
