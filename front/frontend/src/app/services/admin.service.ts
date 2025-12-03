import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AdminService {

  private api = 'http://localhost:8080/api/admin'; // AJUSTE AQUI se necessário

  constructor(private http: HttpClient) {}

  criarAluno(data: any) {
    return this.http.post(`${this.api}/admin/criar-aluno`, data);
  }

  criarProfessor(data: any) {
    return this.http.post(`${this.api}/admin/criar-professor`, data);
  }

  criarNutricionista(data: any) {
    return this.http.post(`${this.api}/admin/criar-nutricionista`, data);
  }

  deletarUsuario(id: string) {
    return this.http.delete(`${this.api}/admin/deletar/${id}`);
  }

  listarUltimos() {
    return this.http.get<any[]>(`${this.api}/admin/ultimos`);
  }
}
