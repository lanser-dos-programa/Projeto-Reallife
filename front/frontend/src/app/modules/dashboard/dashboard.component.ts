import { Component, OnInit, Renderer2 } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { AlunoService } from '../../services/aluno.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  // ============================
  // CALENDÁRIO
  // ============================
  currentMonth: number;
  currentYear: number;
  weeks: number[][] = [];
  monthName: string = '';

  // ============================
  // AULAS (fixas por enquanto)
  // ============================
  aulas = [
    { nome: 'Luta', img: 'assets/luta.jpg' },
    { nome: 'Personal', img: 'assets/personal.jpg' },
    { nome: 'Funcional', img: 'assets/funcional.jpg' }
  ];

  // ============================
  // POPUPS
  // ============================
  mostrarPopup: boolean = false;
  mostrarPopupAlunos: boolean = false;
  mostrarPopupEditarAula: boolean = false;
  mostrarPopupAluno: boolean = false;

  aulaSelecionada: any = null;
  alunoSelecionadoDetalhado: any = null;

  // ============================
  // DADOS VINDOS DO BACKEND
  // ============================
  alunosDetalhados: any[] = [];

  // ============================
  // CONSTRUTOR
  // ============================
  constructor(
    private renderer: Renderer2,
    private router: Router,
    private alunoService: AlunoService
  ) {
    const today = new Date();
    this.currentMonth = today.getMonth();
    this.currentYear = today.getFullYear();
  }

  // ============================
  // ONINIT
  // ============================
  ngOnInit(): void {
    this.generateCalendar(this.currentMonth, this.currentYear);
    this.carregarAlunos();
  }

  // ============================
  // BUSCAR ALUNOS DO BACKEND
  // ============================
  carregarAlunos(): void {
    this.alunoService.getAlunos().subscribe({
      next: (dados) => {
        this.alunosDetalhados = dados;
        console.log("Alunos carregados do backend:", dados);
      },
      error: (erro) => {
        console.error("Erro ao carregar alunos:", erro);
      }
    });
  }

  // ============================
  // CALENDÁRIO
  // ============================
  generateCalendar(month: number, year: number): void {
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const daysInMonth = lastDay.getDate();
    const startDay = firstDay.getDay();

    const weeks: number[][] = [];
    let week: number[] = [];

    for (let i = 0; i < startDay; i++) {
      week.push(0);
    }

    for (let day = 1; day <= daysInMonth; day++) {
      week.push(day);
      if (week.length === 7) {
        weeks.push(week);
        week = [];
      }
    }

    if (week.length > 0) {
      while (week.length < 7) {
        week.push(0);
      }
      weeks.push(week);
    }

    this.weeks = weeks;
    this.monthName = this.getMonthName(month);
  }

  getMonthName(month: number): string {
    const months = [
      'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
      'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
    ];
    return months[month];
  }

  prevMonth(): void {
    if (this.currentMonth === 0) {
      this.currentMonth = 11;
      this.currentYear--;
    } else {
      this.currentMonth--;
    }
    this.generateCalendar(this.currentMonth, this.currentYear);
  }

  nextMonth(): void {
    if (this.currentMonth === 11) {
      this.currentMonth = 0;
      this.currentYear++;
    } else {
      this.currentMonth++;
    }
    this.generateCalendar(this.currentMonth, this.currentYear);
  }

  isToday(day: number): boolean {
    if (day === 0) return false;
    const today = new Date();
    return (
      day === today.getDate() &&
      this.currentMonth === today.getMonth() &&
      this.currentYear === today.getFullYear()
    );
  }

  
  // ============================
  // POPUPS
  // ============================
  abrirPopup(event: Event): void {
    event.preventDefault();
    this.mostrarPopup = true;
  }

  fecharPopup(): void {
    this.mostrarPopup = false;
  }

  abrirPopupAlunos(event: Event): void {
    event.preventDefault();
    this.mostrarPopupAlunos = true;
  }

  fecharPopupAlunos(): void {
    this.mostrarPopupAlunos = false;
  }

  abrirPopupEditar(aula: any): void {
    this.aulaSelecionada = aula;
    this.mostrarPopupEditarAula = true;
  }

  fecharPopupEditar(): void {
    this.aulaSelecionada = null;
    this.mostrarPopupEditarAula = false;
  }

  abrirPopupAluno(aluno: any): void {
    this.alunoSelecionadoDetalhado = aluno;
    this.mostrarPopupAluno = true;
  }

  fecharPopupAluno(): void {
    this.alunoSelecionadoDetalhado = null;
    this.mostrarPopupAluno = false;
  }

  // ============================
  // NAVEGAÇÃO
  // ============================
  abrirTreinoAluno(idAluno: number): void {
    this.router.navigate(['/treinoprof', idAluno]);
  }

  // Placeholder
  salvarAluno(): void {
    console.log('Salvar dados do aluno');
  }

}
