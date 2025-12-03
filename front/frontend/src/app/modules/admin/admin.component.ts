import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { AdminModalComponent } from './modal/modal.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin',
  imports: [AdminModalComponent, CommonModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  ultimosUsuarios: any[] = [];

  modalAberto = false;
  modalTipo = '';
  modalTitulo = '';

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.carregarUltimos();
  }

  abrirModal(tipo: string, titulo: string) {
    this.modalTipo = tipo;
    this.modalTitulo = titulo;
    this.modalAberto = true;
  }

  carregarUltimos() {
    this.adminService.listarUltimos().subscribe((res) => {
      this.ultimosUsuarios = res;
    });
  }
}
