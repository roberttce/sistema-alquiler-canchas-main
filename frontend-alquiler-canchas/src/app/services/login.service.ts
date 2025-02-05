import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import { LoginRequestDTO } from '../models/loginRequest.dto';
import { LoginResponseDTO } from '../models/loginResponse.dto';

@Injectable({
	providedIn: 'root'
})

export class LoginService {

	private apiUrl = 'https://d3frs4kuns9exf.cloudfront.net/api/auth';  // Verifica que la URL sea correcta

  constructor(private http: HttpClient) {}

  login(loginRequestDTO: LoginRequestDTO): Observable<LoginResponseDTO> {
    return this.http.post<LoginResponseDTO>(`${this.apiUrl}/login`, loginRequestDTO);
  }
}