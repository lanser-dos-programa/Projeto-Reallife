import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AulaService } from '../../services/aula.service';

@Component({
  selector: 'app-alunos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './alunos.component.html',
  styleUrls: ['./alunos.component.css']
})
export class AlunosComponent implements OnInit {

  aulas: any[] = [];

  selectedNome: string = '';
  aulaSelecionada: any = null;

  mostrarPopup = false;

  constructor(private aulaService: AulaService) {}

  ngOnInit() {
    this.aulaService.getAulas().subscribe({
      next: (lista) => {
        this.aulas = lista;

        // Inicializa com a primeira aula carregada
        if (this.aulas.length > 0) {
          this.aulaSelecionada = this.aulas[0];
          this.selectedNome = this.aulaSelecionada.nome;
        }
      },
      error: (err) => {
        console.error('Erro ao carregar aulas do backend:', err);
      }
    });
  }

  onSelectedChange(nome: string) {
    const aula = this.aulas.find(a => a.nome === nome);
    if (aula) this.aulaSelecionada = aula;
  }

  abrirPopup() {
    this.mostrarPopup = true;
  }

  fecharPopup() {
    this.mostrarPopup = false;
  }
}
