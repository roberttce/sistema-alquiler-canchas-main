import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ReservaDTO } from '../models/reserva.dto';

@Injectable({
  providedIn: 'root'
})
export class ReservasService {

  private apiUrl = 'https://d3frs4kuns9exf.cloudfront.net/api/reservas';
  // private apiUrl = 'http://localhost:5000/api/reservas';

  constructor(private http: HttpClient) {}

  private handleError(error: HttpErrorResponse) {
    console.error('Error en la API:', error);
    return throwError(() => new Error(error.message || 'Error en la conexión con el servidor.'));
  }

  listarReservas(): Observable<any> {
    return this.http.get<any>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  eliminarReserva(idReserva: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${idReserva}`).pipe(catchError(this.handleError));
  }

  actualizarReserva(idReserva: number, reserva: ReservaDTO): Observable<any> {
    return this.http.put(`${this.apiUrl}/${idReserva}`, reserva).pipe(catchError(this.handleError));
  }

  crearReserva(reserva: ReservaDTO): Observable<ReservaDTO> {
    return this.http.post<ReservaDTO>(this.apiUrl, reserva).pipe(catchError(this.handleError));
  }

  obtenerReservasPorEstado(estado: string): Observable<ReservaDTO[]> {
    return this.http.get<ReservaDTO[]>(`${this.apiUrl}/estado/${estado}`).pipe(catchError(this.handleError));
  }

  obtenerReservasPorCliente(idCliente: number): Observable<ReservaDTO[]> {
    return this.http.get<ReservaDTO[]>(`${this.apiUrl}/cliente/${idCliente}`).pipe(catchError(this.handleError));
  }

  obtenerReservaPorId(idReserva: number): Observable<ReservaDTO> {
    return this.http.get<ReservaDTO>(`${this.apiUrl}/${idReserva}`).pipe(catchError(this.handleError));
  }

  confirmarPagoTotal(idReserva: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${idReserva}/confirmarPago`, {}).pipe(catchError(this.handleError));
  }

  realizarAdelanto(idReserva: number, adelanto: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${idReserva}/adelanto`, { adelanto }).pipe(catchError(this.handleError));
  }

  obtenerReservasPorFecha(fecha: string): Observable<ReservaDTO[]> {
    return this.http.get<ReservaDTO[]>(`${this.apiUrl}/fecha/${fecha}`).pipe(catchError(this.handleError));
  }
}
