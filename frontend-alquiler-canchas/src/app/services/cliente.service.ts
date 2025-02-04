import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { ClienteDTO } from '../models/cliente.dto';

interface GlobalResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private apiUrl = 'http://back-phaqchas-env.eba-ffug332t.us-east-2.elasticbeanstalk.com/api/clientes';  // Cambia según tu URL base

  constructor(private http: HttpClient) { }

  crearCliente(cliente: ClienteDTO): Observable<any> {
      return this.http.post(`${this.apiUrl}`, cliente); // POST para crear cliente
  }

  listarClientes(): Observable<GlobalResponse<ClienteDTO[]>> {
    return this.http.get<GlobalResponse<ClienteDTO[]>>(`${this.apiUrl}`);
  }

  eliminarCliente(idCliente: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${idCliente}`);
  }
    
  actualizarCliente(idCliente: number, cliente: ClienteDTO): Observable<any> {
    return this.http.put(`${this.apiUrl}/${idCliente}`, cliente, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    });
  }

  // Obtener un cliente por ID
  obtenerClientePorId(idCliente: number): Observable<ClienteDTO> {
    return this.http.get<ClienteDTO>(`${this.apiUrl}/${idCliente}`);
  }

  // Obtener un cliente por DNI
  obtenerClientePorDNI(dni: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/dni/${dni}`).pipe(
      map(response => response.data) // Extrae solo la parte "data" del JSON
    );
  }

  // Obtener la cantidad de reservas incompletas de un cliente
  obtenerReservasIncompletas(idCliente: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/${idCliente}/reservas-incompletas`);
  }
}