import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  userName: string = 'Usuario'; // Valor por defecto
  reportDate: string = '2025-02-01'; // Fecha por defecto
  ngOnInit() {
    // Obtener el nombre del usuario de localStorage
    const storedUser = localStorage.getItem('usuario');
    if (storedUser) {
      this.userName = storedUser;
    }
  }
  updateDate(event: any) {
    this.reportDate = event.target.value; // Actualiza la fecha según la selección
  }

}
