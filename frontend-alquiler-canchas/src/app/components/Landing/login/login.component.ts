import { Component } from '@angular/core';
import { AdmiService } from '../../../services/admi.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  usuario: string = '';
  contrasena: string = '';

  constructor(private authService: AdmiService, private router: Router) { }

  login(): void {
    this.authService.login(this.usuario, this.contrasena).subscribe(
      response => {
        this.router.navigate(['/inicio']); 
      },
      error => {
        console.error('Error en el login', error);
      }
    );
  }
}