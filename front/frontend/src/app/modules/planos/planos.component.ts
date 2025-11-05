import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-planos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './planos.component.html',
  styleUrls: ['./planos.component.css']
})
export class PlanosComponent {
  planos = [
    {
      nome: 'PLANO MENSAL',
      preco: 'R$139,00',
      parcelas: '',
      acao: 'EM PROCESSO',
      descricao: 'Plano mensal com acesso completo à academia e aulas coletivas.'
    },
    {
      nome: 'PLANO SEMESTRAL',
      preco: 'R$119,00',
      parcelas: '6x',
      acao: 'ATUALIZAR',
      descricao: 'Plano semestral com acompanhamento e descontos exclusivos.'
    },
    {
      nome: 'PLANO ANUAL',
      preco: 'R$99,00',
      parcelas: '12x',
      acao: 'ATUALIZAR',
      descricao: 'Plano anual com acesso total, suporte e vantagens especiais.'
    }
  ];

  popupAberto = false;
  planoSelecionado: any = null;

  abrirPopup(plano: any) {
    this.planoSelecionado = plano;
    this.popupAberto = true;
  }

  fecharPopup() {
    this.popupAberto = false;
    this.planoSelecionado = null;
  }
}
