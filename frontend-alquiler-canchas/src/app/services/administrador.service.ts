import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AdministradorDTO } from '../models/administrador.dto';
@Injectable({
  providedIn: 'root'
})
export class AdministradorService {

  private apiUrl = 'https://d3frs4kuns9exf.cloudfront.net/api/administradores';  // Cambia según tu URL base

  constructor(private http: HttpClient) { }

    // Método para crear un administrador
    crearAdministrador(administrador: AdministradorDTO): Observable<any> {
        return this.http.post(`${this.apiUrl}/crear`, administrador);
    }

    // Método para listar administradores
    listarAdministradores(): Observable<any> {
        return this.http.get(`${this.apiUrl}/getall`);
    }

    eliminarAdministrador(idAdministrador: number): Observable<any> {
      return this.http.delete(`${this.apiUrl}/${idAdministrador}`);
    }


    actualizarAdministrador(idAdministrador: number, cliente: AdministradorDTO): Observable<any> {
          return this.http.put(`${this.apiUrl}/${idAdministrador}`, cliente, {
            headers: new HttpHeaders({
              'Content-Type': 'application/json'
            })
          });
        }
   




}
