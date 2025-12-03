import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AulaService {

  private api = 'https://localhost:8080/api/aulas';

  constructor(private http: HttpClient) {}

  getAulas(): Observable<any[]> {
    return this.http.get<any[]>(this.api);
  }

  getAulaById(id: number): Observable<any> {
    return this.http.get<any>(`${this.api}/${id}`);
  }

  atualizarAula(id: number, aula: any): Observable<any> {
    return this.http.put<any>(`${this.api}/${id}`, aula);
  }
}
