import { Component } from '@angular/core';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-treino',
  imports: [CommonModule],
  templateUrl: './treino.component.html',
  styleUrls: ['./treino.component.css']
})
export class TreinoComponent {
  dias: string[] = [
    'Segunda',
    'Terça',
    'Quarta',
    'Quinta',
    'Sexta',
    'Sabado',
    'Domingo'
  ];
   treinos: any = {
    Segunda: [
      {
        nome: "Rotação lateral das pernas",
        repeticoes: "6 rep.",
        imagem: "assets/exercicios/rotacao.png"
      },
      {
        nome: "Abdominal invertido",
        repeticoes: "8 rep.",
        imagem: "assets/exercicios/invertido.png"
      },
      {
        nome: "Abdominal em V",
        repeticoes: "10 rep.",
        imagem: "assets/exercicios/v.png"
      }
    ],
    Terça: [
      { nome: "Flexão", repeticoes: "15 rep.", imagem: "assets/exercicios/flexao.png" },
      { nome: "Prancha", repeticoes: "2 min", imagem: "assets/exercicios/prancha.png" }
    ],
    Quarta: [],
    Quinta: [],
    Sexta: [],
    Sábado: [],
    Domingo: []
  };

  
  treinoSelecionado: any[] = [];

  selecionarDia(dia: string) {
    this.treinoSelecionado = this.treinos[dia] || [];
  }
}

