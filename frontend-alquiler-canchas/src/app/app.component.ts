import { CommonModule } from '@angular/common';
import { Component, TemplateRef } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { SidebarComponent } from "./component/sidebar/sidebar.component";
import { LoginComponent } from "./component/login/login/login.component";

@Component({
	selector: 'app-root',
	standalone: true,
	imports: [RouterOutlet, CommonModule],
	providers: [BsModalService],
	templateUrl: './app.component.html',
	styleUrl: './app.component.scss'
})

export class AppComponent {
	person: any = {};

	

	constructor(private router: Router) {}

	cerrarSesion() {
	  // Eliminar datos de autenticación del LocalStorage
	  localStorage.removeItem('token');
	  localStorage.removeItem('usuario');
  
	  // Redirigir al usuario al login
	  this.router.navigate(['/api/auth/login']);
	}
}