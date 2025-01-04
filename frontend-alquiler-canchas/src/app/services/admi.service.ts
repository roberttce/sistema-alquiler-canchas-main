import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class AdmiService {
  private apiUrl = 'http://localhost:8081/api/auth';

  constructor(private http: HttpClient) { }

  login(usuario: string, contrasena: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { usuario, contrasena })
      .pipe(
        tap(response => {
          if (response && response.token) {
            localStorage.setItem('authToken', response.token);
          }
        })
      );
  }

  logout(): void {
    localStorage.removeItem('authToken');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  }



  
}