import { Component, OnInit, Renderer2 } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  currentMonth: number;
  currentYear: number;
  weeks: number[][] = [];
  monthName: string = '';

  aulas = [
    { nome: 'Luta', img: 'assets/luta.jpg' },
    { nome: 'Personal', img: 'assets/personal.jpg' },
    { nome: 'Funcional', img: 'assets/funcional.jpg' }
  ];

  constructor(private renderer: Renderer2, private router: Router) {
    const today = new Date();
    this.currentMonth = today.getMonth();
    this.currentYear = today.getFullYear();
  }

  ngOnInit(): void {
    this.generateCalendar(this.currentMonth, this.currentYear);
  }

  generateCalendar(month: number, year: number): void {
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const daysInMonth = lastDay.getDate();
    const startDay = firstDay.getDay();

    const weeks: number[][] = [];
    let week: number[] = [];

    // Preenche espaços antes do primeiro dia
    for (let i = 0; i < startDay; i++) {
      week.push(0);
    }

    // Preenche dias do mês
    for (let day = 1; day <= daysInMonth; day++) {
      week.push(day);
      if (week.length === 7) {
        weeks.push(week);
        week = [];
      }
    }

    // Completa última semana
    if (week.length > 0) {
      while (week.length < 7) {
        week.push(0);
      }
      weeks.push(week);
    }

    this.weeks = weeks;
    this.monthName = this.getMonthName(month);

console.log('Calendário gerado:', this.weeks);

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

  mostrarPopup: boolean = false;

categoriasTreino = [
  {
    nome: 'Muay Thai',
    professor: 'Gabriel Barbosa',
    horario: 'Quarta-feira - 19:00',
    descricao: 'No Muay Thai, não é apenas sobre lutar, mas sobre se reinventar a cada treino. O foco vai além da técnica — é no seu crescimento pessoal.',
    imagem: 'assets/muay-thai.webp'
  },
  {
    nome: 'Jiu-Jitsu',
    professor: 'Bruno Ferreira',
    horario: 'Segunda e Sexta - 18:30',
    descricao: 'O Jiu-Jitsu é mais do que um esporte — é disciplina, técnica e respeito. Aqui, você aprende a vencer seus próprios limites.',
    imagem: 'assets/jiu-jitsu.jpg'
  },
  {
    nome: 'Boxe',
    professor: 'Carlos Mendes',
    horario: 'Terça e Quinta - 20:00',
    descricao: 'Com o boxe, você desenvolve foco, força e agilidade. Um treino completo que trabalha corpo e mente.',
    imagem: 'assets/boxe.jpg'
  }
];

abrirPopup(event: Event): void {
  event.preventDefault();
  this.mostrarPopup = true;
}

fecharPopup(): void {
  this.mostrarPopup = false;
}

  mostrarPopupAlunos: boolean = false;

alunosDetalhados = [
  { nome: 'LUIZ DANIEL LANSER', peso: '1T', altura: '1,79m', nascimento: '22/02/1998' },
  { nome: 'PABLO HENRIQUE DE ANDRADE', peso: '45kg', altura: '1,90m', nascimento: '20/10/1945' },
  { nome: 'BRUNO VALCANAIA', peso: '-65kg', altura: '1,80m', nascimento: '14/02/2000' },
  { nome: 'CAIO FELIPE', peso: '-45kg', altura: '1m', nascimento: '23/07/6000' },
  { nome: 'VITOR HUGO', peso: '60kg', altura: '1,77m', nascimento: '24/10/1929' }
];

abrirPopupAlunos(event: Event): void {
  event.preventDefault();
  this.mostrarPopupAlunos = true;
}

fecharPopupAlunos(): void {
  this.mostrarPopupAlunos = false;
}

mostrarPopupEditarAula: boolean = false;
aulaSelecionada: any = null;

abrirPopupEditar(aula: any): void {
  this.aulaSelecionada = aula;
  this.mostrarPopupEditarAula = true;
}

fecharPopupEditar(): void {
  this.mostrarPopupEditarAula = false;
  this.aulaSelecionada = null;
}

mostrarPopupAluno: boolean = false;
alunoSelecionadoDetalhado: any = null;

abrirPopupAluno(aluno: any): void {

  this.alunoSelecionadoDetalhado = aluno;
  this.mostrarPopupAluno = true;
}

fecharPopupAluno(): void {
  this.mostrarPopupAluno = false;
  this.alunoSelecionadoDetalhado = null;
}


abrirTreinoAluno(idAluno: number) {
  this.router.navigate(['/treinoprof', idAluno]);

  // Aqui você pode abrir outro popup de treino se quiser
}

salvarAluno(): void {
  console.log('Salvar dados do aluno');
  // Aqui você pode adicionar lógica de salvar depois
}

}
