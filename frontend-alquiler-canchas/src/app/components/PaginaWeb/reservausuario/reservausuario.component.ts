import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservaService } from '../../../services/reservauasuario.service';

@Component({
  selector: 'app-reservausuario',
  standalone: true,
  imports: [CommonModule], // Importa CommonModule aquí
  templateUrl: './reservausuario.component.html',
  styleUrl: './reservausuario.component.scss'
})
export class ReservausuarioComponent implements OnInit {
  reservas: any[] = [];

  
  constructor(private reservaService: ReservaService) { }

  ngOnInit(): void {
    this.reservaService.getReservas().subscribe(
      data => {
        this.reservas = data;
      },
      error => {
        console.error('Error al obtener las reservas', error);
      }
    );
  }
}