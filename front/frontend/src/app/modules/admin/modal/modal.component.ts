import { Component, EventEmitter, Input, Output, OnChanges } from '@angular/core';
import { AdminService } from '../../../services/admin.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-modal-admin',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class AdminModalComponent implements OnChanges {

  @Input() tipo: string = '';
  @Input() titulo: string = '';
  @Output() fechar = new EventEmitter();
  @Output() atualizado = new EventEmitter();

  form: any = {
    nome: '',
    email: '',
    id: null
  };

  constructor(private adminService: AdminService) {}

  // Limpa o form sempre que o modal abrir
  ngOnChanges() {
    this.form = { nome: '', email: '', id: null };
  }

  close() {
    this.fechar.emit();
  }

  submit() {

    // validações básicas
    if (this.tipo !== 'deletar' && (!this.form.nome || !this.form.email)) {
      alert('Preencha nome e email.');
      return;
    }

    if (this.tipo === 'deletar' && !this.form.id) {
      alert('Informe um ID para deletar.');
      return;
    }

    // chamadas do service
    if (this.tipo === 'aluno') {
      this.adminService.criarAluno(this.form).subscribe(() => this.finalizar());
    }

    if (this.tipo === 'professor') {
      this.adminService.criarProfessor(this.form).subscribe(() => this.finalizar());
    }

    if (this.tipo === 'nutricionista') {
      this.adminService.criarNutricionista(this.form).subscribe(() => this.finalizar());
    }

    if (this.tipo === 'deletar') {
      this.adminService.deletarUsuario(this.form.id).subscribe(() => this.finalizar());
    }
  }

  finalizar() {
    this.atualizado.emit(); // Atualiza lista do admin
    this.close();           // Fecha o modal
  }
}
