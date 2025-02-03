import { Component } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, UntypedFormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../../services/login.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoginResponseDTO } from '../../../models/loginResponse.dto';
import { LoginRequestDTO } from '../../../models/loginRequest.dto';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
	
  usuario: string = '';
  contrasena: string = '';

  constructor(
    private loginService: LoginService,
    private router: Router
  ) {}

  onLogin(): void {
    const loginRequest: LoginRequestDTO = {
      usuario: this.usuario,
      contrasena: this.contrasena,
    };

    this.loginService.login(loginRequest).subscribe(
      (response: LoginResponseDTO) => {
        console.log('Login exitoso', response);

        // ✅ Guardar en localStorage solo lo necesario
        localStorage.setItem('usuario', this.usuario);
        localStorage.setItem('token', response.token);

        // 🔐 (Opcional) Guardar en sessionStorage en lugar de localStorage si quieres que se borre al cerrar la pestaña
        // sessionStorage.setItem('usuario', this.usuario);
        // sessionStorage.setItem('token', response.token);

        // 🔄 Redirigir a la página principal
        this.router.navigate(['inicio']);
      },
      (error) => {
        console.error('Error en el login', error);
        alert('Usuario o contraseña incorrectos'); // Muestra un mensaje al usuario
      }
    );
  }
}
