import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DietaService {

  private apiUrl = 'http://localhost:8080/dietas';

  constructor(private http: HttpClient) {}

  getDietaPorAluno(idAluno: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${idAluno}`);
  }
  salvarDieta(idAluno: number, dieta: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/${idAluno}`, dieta);
  }

  getAlimentos() {
  return this.http.get<any[]>(`${this.apiUrl}/alimentos`);
}

}
