import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlunoService {

  private api = 'https://localhost:8080/api/alunos';

  constructor(private http: HttpClient) {}

  getAlunos(): Observable<any[]> {
    return this.http.get<any[]>(this.api);
  }

  getAlunoById(id: number): Observable<any> {
    return this.http.get<any>(`${this.api}/${id}`);
  }

  atualizarAluno(id: number, aluno: any): Observable<any> {
    return this.http.put<any>(`${this.api}/${id}`, aluno);
  }

  getTreinoAluno(id: number): Observable<any> {
    return this.http.get<any>(`${this.api}/${id}/treino`);
  }
}
