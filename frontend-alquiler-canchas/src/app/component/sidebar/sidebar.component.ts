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

  ngOnInit() {
    // Obtener el nombre del usuario de localStorage
    const storedUser = localStorage.getItem('usuario');
    if (storedUser) {
      this.userName = storedUser;
    }
  }


}
