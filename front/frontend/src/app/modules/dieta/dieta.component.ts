import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DietaService } from '../../services/dieta.service';

@Component({
  selector: 'app-dieta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dieta.component.html',
  styleUrls: ['./dieta.component.css']
})
export class DietaComponent implements OnInit {

  dias = ['Segunda','Terça','Quarta','Quinta','Sexta','Sábado','Domingo'];

  dietaSemanal: any = {};
  refeicoesSelecionadas: any[] = [];
  idAluno = 1; // depois você pega do login

  constructor(private dietaService: DietaService) {}

  ngOnInit() {
    this.dietaService.getDietaPorAluno(this.idAluno).subscribe({
      next: (data) => this.dietaSemanal = data,
      error: (err) => console.error(err)
    });
  }

  selecionarDia(dia: string) {
    this.refeicoesSelecionadas = this.dietaSemanal[dia] || [];
  }
}
