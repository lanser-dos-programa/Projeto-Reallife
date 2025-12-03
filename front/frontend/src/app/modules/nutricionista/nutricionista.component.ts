import { Component, Renderer2, OnDestroy, OnInit, Inject } from '@angular/core';
import { CommonModule, DOCUMENT } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DietaService } from '../../services/dieta.service';


@Component({
    standalone: true,  
    selector: 'app-dieta-form',
    templateUrl: './nutricionista.component.html',
    styleUrls: ['./nutricionista.component.css'],
    imports: [CommonModule, FormsModule]
})  

export class NutricionistaComponent implements OnInit, OnDestroy {
    
id!: number;
nome!: string;
    
    dias = ['Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta'];  
    diaSelecionado: string = 'Segunda';
    
    refeicoes = [
        { id: 'cafe', nome: 'Café da manhã' },  
    { id: 'lanche_manha', nome: 'Lanche da manhã' },
    { id: 'almoco', nome: 'Almoço' },
    { id: 'lanche_tarde', nome: 'Lanche da tarde' },
    { id: 'pre_treino', nome: 'Pré-treino' },
    { id: 'pos_treino', nome: 'Pós-treino' },
    { id: 'jantar', nome: 'Jantar' },
];  

alimentos: any[] = [];  

  // Estrutura principal
  dieta: any = {};

  totaisPorRefeicao: any = {};
  totalDoDia: number = 0;
  
  
  constructor(private renderer: Renderer2, private route: ActivatedRoute, private dietaService: DietaService,) {
    this.dias.forEach(dia => {
    this.dieta[dia] = {};

    this.refeicoes.forEach(ref => {
      this.dieta[dia][ref.id] = [];
    });
  });
}

ngOnInit() {
  this.renderer.addClass(document.body, 'scroll-liberado');
  this.id = Number(this.route.snapshot.paramMap.get('id'));
  this.nome = decodeURIComponent(this.route.snapshot.paramMap.get('nome') || '');

   this.dietaService.getAlimentos().subscribe({
    next: (lista) => {
      this.alimentos = lista;   // agora o HTML pega daqui
    },
    error: () => console.error('Erro ao carregar alimentos')
  });

}

ngOnDestroy() {
  this.renderer.removeClass(document.body, 'scroll-liberado');
}


selecionarDia(dia: string) {
    this.diaSelecionado = dia;  
    this.atualizarCalorias();
  }  


  adicionarAlimento(ref: string) {
    this.dieta[this.diaSelecionado][ref].push({
      alimento: null,  
      quantidade: 0,
      kcal: 0
    });  
  }  

  removerAlimento(ref: string, index: number) {
    this.dieta[this.diaSelecionado][ref].splice(index, 1);  
    this.atualizarCalorias();
  }  


atualizarCalorias() {

  let totalDia = 0;
  this.totaisPorRefeicao = {};

  for (let ref of this.refeicoes) {

    let totalRefeicao = 0;

    for (let item of this.dieta[this.diaSelecionado][ref.id]) {

      if (!item.alimento || !item.quantidade) {
        item.kcal = 0;
        continue;
      }

      // Agora o backend SEMPRE envia kcal por medida
      item.kcal = item.quantidade * item.alimento.kcal;

      totalRefeicao += item.kcal;
    }

    this.totaisPorRefeicao[ref.id] = totalRefeicao;
    totalDia += totalRefeicao;
  }

  this.totalDoDia = totalDia;
}

  salvarDieta() {
  this.dietaService.salvarDieta(this.id, this.dieta).subscribe({
    next: () => alert('Dieta enviada ao aluno!'),
    error: () => alert('Erro ao salvar dieta!')
  });
}

    filtrarAlimentos(item: any) {
  const termo = item.search?.toLowerCase() || '';      
  item.filtrados = this.alimentos.filter(a =>
    a.nome.toLowerCase().includes(termo)
  );  
}  

selecionarAlimento(item: any, alimento: any) {
  item.alimento = alimento;
  item.search = alimento.nome;
  item.filtrados = [];

  // Salva a medida que veio do backend
  item.medida = alimento.medida;

  this.atualizarCalorias();
}


}
