import { Component, Renderer2, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-treino-professor',
  templateUrl: './treinoprof.component.html',
  styleUrls: ['./treinoprof.component.css'],
  imports: [CommonModule, FormsModule]
})
export class TreinoprofComponent implements OnInit, OnDestroy {

  constructor(private renderer: Renderer2) {
    this.dias.forEach(dia => {
      this.treino[dia] = [];
    });
  }

  ngOnInit() {
    this.renderer.addClass(document.querySelector('app-root'), 'scroll-liberado');
  }

  ngOnDestroy() {
    this.renderer.removeClass(document.querySelector('app-root'), 'scroll-liberado');
  }

  dias = ['Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta'];
  diaSelecionado: string = '';

  exercicios = [
    { nome: 'Supino reto' },
    { nome: 'Agachamento' },
    { nome: 'Remada baixa' },
    { nome: 'Desenvolvimento' },
    { nome: 'Rosca direta' },
    { nome: 'Puxada aberta' }
  ];

  treino: any = {};
  totaisPorDia: any = {};
  totalDoDia: number = 0;

 

  selecionarDia(dia: string) {
    this.diaSelecionado = dia;
  }

  adicionarExercicio() {
    this.treino[this.diaSelecionado].push({
      exercicio: null,
      series: null,
      repeticoes: null,
      descanso: null
    });
  }

  removerExercicio(index: number) {
    this.treino[this.diaSelecionado].splice(index, 1);
  }

  salvarTreino() {
    console.log("Treino salvo:", this.treino[this.diaSelecionado]);
    alert("Treino enviado para o aluno!");
  }
}
