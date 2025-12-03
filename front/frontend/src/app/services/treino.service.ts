import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TreinoService {

  private api = 'http://localhost:8080/api/treinos';

  constructor(private http: HttpClient) {}

  getExercicios(): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/exercicios`);
  }

  salvarTreino(treino: any): Observable<any> {
    return this.http.post(`${this.api}`, treino);
  }
}
