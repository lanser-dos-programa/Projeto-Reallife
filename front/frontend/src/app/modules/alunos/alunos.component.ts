import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { trigger, transition, style, animate } from '@angular/animations';


@Component({
  selector: 'app-alunos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './alunos.component.html',
  styleUrls: ['./alunos.component.css'],
  animations: [
    trigger('fadeScale', [
      transition(':enter', [
        style({ opacity: 0, transform: 'scale(0.95)' }),
        animate('250ms ease-out', style({ opacity: 1, transform: 'scale(1)' }))
      ]),
      transition(':leave', [
        animate('200ms ease-in', style({ opacity: 0, transform: 'scale(0.95)' }))
      ])
    ])
  ]
})
export class AlunosComponent {
  aulas = [
    {
      nome: 'Muay Thai',
      descricao: 'NO MUAY THAI, NÃO É APENAS SOBRE LUTAR, MAS SOBRE SE REINVENTAR A CADA TREINO...',
      professor: 'SOU O MICHAEL...',
      foto: 'https://placehold.co/60x60/555/fff?text=M',
      imagens: [
        'https://placehold.co/600x600/333/808080?text=Treino+1',
        'https://placehold.co/400x300/333/808080?text=Luta+no+Ringue',
        'https://placehold.co/400x300/333/808080?text=Sensei'
      ],
      horarios: [
        { dia: 'Segunda-feira', hora: '18:00 - 19:00' },
        { dia: 'Quarta-feira', hora: '18:00 - 19:00' },
        { dia: 'Sexta-feira', hora: '19:00 - 20:00' }
      ]
    },
    {
      nome: 'Boxe',
      descricao: 'NO BOXE, O FOCO ESTÁ NA PRECISÃO, NA TÉCNICA E NA MENTE.',
      professor: 'SOU O JOÃO...',
      foto: 'https://placehold.co/60x60/555/fff?text=J',
      imagens: [
        'https://placehold.co/600x600/333/808080?text=Treino+de+Boxe',
        'https://placehold.co/400x300/333/808080?text=Sparring',
        'https://placehold.co/400x300/333/808080?text=Técnica'
      ],
      horarios: [
        { dia: 'Terça-feira', hora: '17:00 - 18:30' },
        { dia: 'Quinta-feira', hora: '17:00 - 18:30' }
      ]
    },
    {
      nome: 'Jiu-Jitsu',
      descricao: 'NO JIU-JITSU, A FORÇA NÃO VEM DO TAMANHO...',
      professor: 'SOU O RAFAEL...',
      foto: 'https://placehold.co/60x60/555/fff?text=R',
      imagens: [
        'https://placehold.co/600x600/333/808080?text=Chave+de+Braço',
        'https://placehold.co/400x300/333/808080?text=Montada',
        'https://placehold.co/400x300/333/808080?text=Sensei'
      ],
      horarios: [
        { dia: 'Segunda-feira', hora: '19:00 - 20:00' },
        { dia: 'Quarta-feira', hora: '19:00 - 20:00' },
        { dia: 'Sexta-feira', hora: '18:00 - 19:00' }
      ]
    },
    {
      nome: 'Karatê',
      descricao: 'O KARATÊ É UM CAMINHO DE AUTOCONTROLE...',
      professor: 'SOU O TAKEDA...',
      foto: 'https://placehold.co/60x60/555/fff?text=T',
      imagens: [
        'https://placehold.co/600x600/333/808080?text=Kata',
        'https://placehold.co/400x300/333/808080?text=Treino',
        'https://placehold.co/400x300/333/808080?text=Sensei'
      ],
      horarios: [
        { dia: 'Terça-feira', hora: '18:00 - 19:30' },
        { dia: 'Quinta-feira', hora: '18:00 - 19:30' }
      ]
    }
  ];

  selectedNome = this.aulas[0].nome;
  aulaSelecionada = this.aulas[0];
  mostrarPopup = false;

  onSelectedChange(nome: string) {
    const encontrada = this.aulas.find(a => a.nome === nome);
    if (encontrada) this.aulaSelecionada = encontrada;
  }

  abrirPopup() {
    this.mostrarPopup = true;
  }

  fecharPopup() {
    this.mostrarPopup = false;
  }
}
