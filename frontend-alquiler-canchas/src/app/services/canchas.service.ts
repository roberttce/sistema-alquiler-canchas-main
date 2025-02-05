import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface GlobalResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

interface CanchaDTO {
  id: number;
  nombre: string;
  ubicacion: string;
  costo: number;
}

interface CanchaDeporteDTO {
  idCancha: number;
  idDeporte: number;
}

@Injectable({
  providedIn: 'root'
})
export class CanchasService {
  private apiUrl = 'https://d3frs4kuns9exf.cloudfront.net/api/canchas';

  constructor(private http: HttpClient) {}

  crearCancha(cancha: CanchaDTO): Observable<GlobalResponse<CanchaDTO>> {
    return this.http.post<GlobalResponse<CanchaDTO>>(this.apiUrl, cancha);
  }

  obtenerCanchaPorId(idCancha: number): Observable<GlobalResponse<CanchaDTO>> {
    return this.http.get<GlobalResponse<CanchaDTO>>(`${this.apiUrl}/${idCancha}`);
  }

  listarCanchas(): Observable<GlobalResponse<CanchaDTO[]>> {
    return this.http.get<GlobalResponse<CanchaDTO[]>>(this.apiUrl);
  }

  actualizarCancha(idCancha: number, cancha: CanchaDTO): Observable<GlobalResponse<CanchaDTO>> {
    return this.http.put<GlobalResponse<CanchaDTO>>(`${this.apiUrl}/${idCancha}`, cancha);
  }

  eliminarCancha(idCancha: number): Observable<GlobalResponse<void>> {
    return this.http.delete<GlobalResponse<void>>(`${this.apiUrl}/${idCancha}`);
  }

  asociarDeportesACancha(idCancha: number, idDeportes: number[]): Observable<GlobalResponse<CanchaDeporteDTO[]>> {
    return this.http.post<GlobalResponse<CanchaDeporteDTO[]>>(`${this.apiUrl}/${idCancha}/deportes`, idDeportes);
  }
}
