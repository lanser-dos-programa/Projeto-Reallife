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
}
