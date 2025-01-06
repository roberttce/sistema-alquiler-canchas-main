import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {
  private apiUrl = 'http://localhost:8081/api/reservas';

  constructor(private http: HttpClient) { }

  getReservas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}