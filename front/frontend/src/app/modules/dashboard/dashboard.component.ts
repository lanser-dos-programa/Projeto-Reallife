import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  currentDate = new Date();
  currentMonth!: number;
  currentYear!: number;
  weeks: (number | null)[][] = [[]];

  alunos = [
    { nome: 'LUIZ DANIEL LANSER' },
    { nome: 'PABLO HENRIQUE DE ANDRADE' },
    { nome: 'BRUNO VALCANAIA' },
    { nome: 'CAIO FELIPE' }
  ];

  aulas = [
    { nome: 'Luta', img: 'assets/luta.jpg' },
    { nome: 'Personal', img: 'assets/personal.jpg' },
    { nome: 'Funcional', img: 'assets/funcional.jpg' }
  ];

  ngOnInit() {
    this.currentMonth = this.currentDate.getMonth();
    this.currentYear = this.currentDate.getFullYear();
    this.generateCalendar(this.currentYear, this.currentMonth);
  }

  generateCalendar(year: number, month: number) {
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const weeks: (number | null)[][] = [];

    let currentWeek: (number | null)[] = [];

    // Preenche dias vazios até o primeiro dia da semana
    for (let i = 0; i < firstDay.getDay(); i++) {
      currentWeek.push(null);
    }

    // Preenche os dias do mês
    for (let day = 1; day <= lastDay.getDate(); day++) {
      currentWeek.push(day);

      // Quando chega ao sábado (6), fecha a semana
      if (currentWeek.length === 7) {
        weeks.push(currentWeek);
        currentWeek = [];
      }
    }

    // Preenche dias vazios no fim do mês, se necessário
    if (currentWeek.length > 0) {
      while (currentWeek.length < 7) {
        currentWeek.push(null);
      }
      weeks.push(currentWeek);
    }

    this.weeks = weeks;
    console.log('Calendário gerado:', this.weeks);
}


  
  previousMonth() {
    if (this.currentMonth === 0) {
      this.currentMonth = 11;
      this.currentYear--;
    } else {
      this.currentMonth--;
    }
    this.generateCalendar(this.currentYear, this.currentMonth);
  }

  nextMonth() {
    if (this.currentMonth === 11) {
      this.currentMonth = 0;
      this.currentYear++;
    } else {
      this.currentMonth++;
    }
    this.generateCalendar(this.currentYear, this.currentMonth);
  }

  isToday(day: number | null): boolean {
  if (!day) return false;
  const today = new Date();
  return (
    day === today.getDate() &&
    this.currentMonth === today.getMonth() &&
    this.currentYear === today.getFullYear()
  );
}

  get monthName() {
    const months = [
      'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
      'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
    ];
    return months[this.currentMonth];

    

}
}