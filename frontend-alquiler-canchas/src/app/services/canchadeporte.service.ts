import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface GlobalResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

interface CanchaDeporteDTO {
  idCanchaDeporte: number;
  idCancha: number;
  idDeporte: number;
}

interface DeporteDTO {
  idDeporte: number;
  nombre: string;
}

@Injectable({
  providedIn: 'root'
})
export class CanchadeporteService {
  private apiUrl = 'http://back-phaqchas-env.eba-ffug332t.us-east-2.elasticbeanstalk.com/api/canchas-deporte';

  constructor(private http: HttpClient) { }

  crearCanchaDeporte(canchaDeporte: CanchaDeporteDTO): Observable<GlobalResponse<CanchaDeporteDTO>> {
    return this.http.post<GlobalResponse<CanchaDeporteDTO>>(this.apiUrl, canchaDeporte);
  }

  obtenerCanchaDeporte(idCancha: number, idDeporte: number): Observable<GlobalResponse<CanchaDeporteDTO>> {
    return this.http.get<GlobalResponse<CanchaDeporteDTO>>(`${this.apiUrl}/${idCancha}/${idDeporte}`);
  }

  listarCanchasDeportes(): Observable<GlobalResponse<CanchaDeporteDTO[]>> {
    return this.http.get<GlobalResponse<CanchaDeporteDTO[]>>(this.apiUrl);
  }

  listarCanchasPorDeporte(idDeporte: number): Observable<GlobalResponse<CanchaDeporteDTO[]>> {
    return this.http.get<GlobalResponse<CanchaDeporteDTO[]>>(`${this.apiUrl}/deporte/${idDeporte}`);
  }

  listarDeportesPorCancha(idCancha: number): Observable<GlobalResponse<DeporteDTO[]>> {
    return this.http.get<GlobalResponse<DeporteDTO[]>>(`${this.apiUrl}/cancha/${idCancha}/deportes`);
  }

  eliminarCanchaDeporte(idCancha: number, idDeporte: number): Observable<GlobalResponse<void>> {
    return this.http.delete<GlobalResponse<void>>(`${this.apiUrl}/${idCancha}/${idDeporte}`);
  }
}
