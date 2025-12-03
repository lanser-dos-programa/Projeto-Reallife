import { Component, Renderer2, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AlunoService } from '../../services/aluno.service';
import { TreinoService } from '../../services/treino.service';

@Component({
  standalone: true,
  selector: 'app-treino-professor',
  templateUrl: './treinoprof.component.html',
  styleUrls: ['./treinoprof.component.css'],
  imports: [CommonModule, FormsModule]
})
export class TreinoprofComponent implements OnInit, OnDestroy {

  alunoId!: number;
  alunoNome: string = '';

  constructor(
    private renderer: Renderer2,
    private route: ActivatedRoute,
    private alunoService: AlunoService,
    private treinoService: TreinoService
  ) {
    this.dias.forEach(dia => {
      this.treino[dia] = [];
    });
  }

  ngOnInit() {
    this.renderer.addClass(document.querySelector('app-root'), 'scroll-liberado');

    // 📌 pegar o ID da URL
    this.alunoId = Number(this.route.snapshot.paramMap.get('id'));

    // 📌 buscar aluno no backend
    this.alunoService.getAlunoById(this.alunoId).subscribe({
      next: (aluno) => {
        this.alunoNome = aluno.nome;
      }
    });

    // 📌 carregar exercícios do backend
    this.carregarExercicios();
  }

  ngOnDestroy() {
    this.renderer.removeClass(document.querySelector('app-root'), 'scroll-liberado');
  }

  dias = ['Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta'];
  diaSelecionado: string = '';

  exercicios: any[] = [];

  treino: any = {};

  // ======================================
  // 📌 1 — BUSCAR EXERCÍCIOS DO BACKEND
  // ======================================
  carregarExercicios() {
    this.treinoService.getExercicios().subscribe({
      next: (lista) => this.exercicios = lista,
      error: (err) => console.error("Erro ao carregar exercícios:", err)
    });
  }

  selecionarDia(dia: string) {
    this.diaSelecionado = dia;
  }

  adicionarExercicio() {
    this.treino[this.diaSelecionado].push({
      exercicioId: null,
      series: null,
      repeticoes: null,
      descanso: null
    });
  }

  removerExercicio(index: number) {
    this.treino[this.diaSelecionado].splice(index, 1);
  }

  // ======================================
  // 📌 2 — ENVIAR TREINO PARA O BACKEND
  // ======================================
  salvarTreino() {
    const treinoFinal = {
      alunoId: this.alunoId,
      nomeAluno: this.alunoNome,
      dias: this.treino
    };

    this.treinoService.salvarTreino(treinoFinal).subscribe({
      next: () => alert("Treino enviado com sucesso!"),
      error: (err) => console.error("Erro ao enviar treino:", err)
    });
  }

}
