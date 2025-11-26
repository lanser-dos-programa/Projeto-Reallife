import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

@Component({
  selector: 'app-dieta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dieta.component.html',
  styleUrls: ['./dieta.component.css']
})
export class DietaComponent {

  dias = [
    'Segunda', 'Terça', 'Quarta',
    'Quinta', 'Sexta', 'Sábado', 'Domingo'
  ];

  // ----- DIETA ENVIADA PELA NUTRICIONISTA -----
  dietaSemanal: any = {

    Segunda: [
      {
        refeicao: "Café da Manhã",
        descricao: "Omelete com 3 ovos + 1 fatia de pão integral + 1 fruta",
        calorias: "350 kcal",
        imagem: "assets/dieta/cafe1.png"
      },
      {
        refeicao: "Almoço",
        descricao: "Arroz, feijão, frango grelhado e salada",
        calorias: "500 kcal",
        imagem: "assets/dieta/almoco1.png"
      },
      {
        refeicao: "Lanche da Tarde",
        descricao: "Iogurte natural com granola",
        calorias: "180 kcal",
        imagem: "assets/dieta/lanche1.png"
      },
      {
        refeicao: "Jantar",
        descricao: "Peixe assado com legumes",
        calorias: "420 kcal",
        imagem: "assets/dieta/jantar1.png"
      }
    ],

    Terça: [
      {
        refeicao: "Café da Manhã",
        descricao: "Vitamina de banana com aveia e leite",
        calorias: "300 kcal",
        imagem: "assets/dieta/cafe2.png"
      }
    ],

    Quarta: [],
    Quinta: [],
    Sexta: [],
    Sábado: [],
    Domingo: []
  };

  refeicoesSelecionadas: any[] = [];

  selecionarDia(dia: string) {
    this.refeicoesSelecionadas = this.dietaSemanal[dia] || [];
  }
}
