import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-alunos',
  standalone: true, // 👈 obrigatório em projetos sem módulo
  imports: [CommonModule, FormsModule], // 👈 importa aqui
  templateUrl: './alunos.component.html',
  styleUrls: ['./alunos.component.css']
})
export class AlunosComponent {
  aulas = [
    {
      nome: 'Muay Thai',
      descricao: 'NO MUAY THAI, NÃO É APENAS SOBRE LUTAR, MAS SOBRE SE REINVENTAR A CADA TREINO. AQUI, O FOCO VAI ALÉM DA TÉCNICA – É NO SEU CRESCIMENTO PESSOAL, TANTO DENTRO DO RINGUE QUANTO NA SUA VIDA.',
      professor: 'SOU O MICHAEL, EX-LUTADOR PROFISSIONAL DE MUAY THAI E AGORA SENSEI. MINHA MISSÃO É ENSINAR NÃO SÓ A TÉCNICA, MAS TAMBÉM DISCIPLINA E RESPEITO. SE VOCÊ AMA ESSA ARTE, VAMOS JUNTOS EVOLUIR!',
      foto: 'https://placehold.co/60x60/555/fff?text=M',
      imagens: [
        'https://placehold.co/600x600/333/808080?text=Treino+1',
        'https://placehold.co/400x300/333/808080?text=Luta+no+Ringue',
        'https://placehold.co/400x300/333/808080?text=Sensei'
      ]
    },
    {
      nome: 'Boxe',
      descricao: 'NO BOXE, O FOCO ESTÁ NA PRECISÃO, NA TÉCNICA E NA MENTE.',
      professor: 'SOU O JOÃO, TREINADOR DE BOXE.',
      foto: 'https://placehold.co/60x60/555/fff?text=J',
      imagens: [
        'https://placehold.co/600x600/333/808080?text=Treino+de+Boxe',
        'https://placehold.co/400x300/333/808080?text=Sparring',
        'https://placehold.co/400x300/333/808080?text=Técnica'
      ]
    },
    {
      nome: 'Jiu-Jitsu',
      descricao: 'NO JIU-JITSU, A FORÇA NÃO VEM DO TAMANHO, MAS DA TÉCNICA E DA PACIÊNCIA.',
      professor: 'SOU O RAFAEL, FAIXA PRETA DE JIU-JITSU.',
      foto: 'https://placehold.co/60x60/555/fff?text=R',
      imagens: [
        'https://placehold.co/600x600/333/808080?text=Chave+de+Braço',
        'https://placehold.co/400x300/333/808080?text=Montada',
        'https://placehold.co/400x300/333/808080?text=Sensei'
      ]
    },
    {
      nome: 'Karatê',
      descricao: 'O KARATÊ É UM CAMINHO DE AUTOCONTROLE, FOCO E EQUILÍBRIO.',
      professor: 'SOU O TAKEDA, 4º DAN DE KARATÊ.',
      foto: 'https://placehold.co/60x60/555/fff?text=T',
      imagens: [
        'https://placehold.co/600x600/333/808080?text=Kata',
        'https://placehold.co/400x300/333/808080?text=Treino',
        'https://placehold.co/400x300/333/808080?text=Sensei'
      ]
    }
  ];

  // value usado pelo ngModel
  selectedNome = this.aulas[0].nome;

  // objeto exibido na UI
  aulaSelecionada = this.aulas[0];

  // executado quando o select muda
  onSelectedChange(nome: string) {
    const encontrada = this.aulas.find(a => a.nome === nome);
    if (encontrada) {
      this.aulaSelecionada = encontrada;
    } else {
      // fallback
      this.aulaSelecionada = this.aulas[0];
    }
  }
}
