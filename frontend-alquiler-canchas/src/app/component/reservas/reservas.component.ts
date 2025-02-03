import { Component, TemplateRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import Swal from 'sweetalert2';
import { ReservaDTO } from '../../models/reserva.dto';
import { ReservasService } from '../../services/reserva.service';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../services/cliente.service';
import { CanchadeporteService } from '../../services/canchadeporte.service';
import { SidebarComponent } from "../sidebar/sidebar.component";
import { CanchasService } from '../../services/canchas.service';

@Component({
  selector: 'app-reservas',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, SidebarComponent],
  templateUrl: './reservas.component.html',
  styleUrls: ['./reservas.component.scss']
})
export class ReservasComponent {
  reservas: ReservaDTO[] = [];
  loading = true;
  errorMessage = '';
  editForm: FormGroup;
  createForm: FormGroup;
  modalRef?: BsModalRef;
  selectedReservaId?: number;

  clientes: any[] = []; // Lista de clientes
  canchadeporte: any[] = []; // Lista de canchas
  cancha: any[] = [];

  constructor(  
    private reservasService: ReservasService,
    private clienteService: ClienteService,
    private canchadeporteservice: CanchadeporteService,
    private canchas: CanchasService,
    public modalService: BsModalService,
    private fb: FormBuilder
  ) {
    this.editForm = this.fb.group({
      fechaReserva: ['', Validators.required],
      horaInicio: ['', Validators.required],
      costoTotal: ['', Validators.required],
      adelanto: ['', Validators.required],
      idCliente: ['', Validators.required],
      idCanchaDeporte: ['', Validators.required],
      estado: ['', Validators.required]
    });

    this.createForm = this.fb.group({
      fechaReserva: ['', Validators.required],
      horaInicio: ['', Validators.required],
      adelanto: ['', Validators.required],
      idCliente: ['', Validators.required],
      idCanchaDeporte: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.fetchCanchas();
    this.obtenerReservas();
    this.fetchClientes();
    this.fetchCanchasDeporte();
  }

  obtenerReservas(): void {
    this.reservasService.listarReservas().subscribe({
      next: (response) => {
        this.reservas = response.data || [];
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Error al cargar las reservas';
        this.loading = false;
      }
    });
  }

  fetchClientes() {
    this.clienteService.listarClientes().subscribe(response => {
      this.clientes = response.data;
    });
  }

  fetchCanchasDeporte() {
    this.canchadeporteservice.listarCanchasDeportes().subscribe(response => {
      this.canchadeporte = response.data;
      console.log('CanchasDeporte cargadas:', this.canchadeporte);
    });
  }

  fetchCanchas(){
    this.canchas.listarCanchas().subscribe(Response =>{
      this.cancha = Response.data;
      console.log('Canchas cargadas:', this.cancha);
    })
  }

  getNombreCliente(idCliente: number): string {
    const cliente = this.clientes.find(c => c.idCliente === idCliente);
    return cliente ? cliente.nombre : 'Desconocido';
  }

  getNombreCancha(idCanchaDeporte: number): string {
    // const cancha = this.canchadeporte.find(c => c.idCanchaDeporte === idCanchaDeporte);
    // return cancha ? cancha.idCancha : 'Desconocido';
    console.log('Buscando nombre para idCanchaDeporte:', idCanchaDeporte);
      // Encuentra la relación CanchaDeporte
    const relacion = this.canchadeporte.find(c => c.idCanchaDeporte === idCanchaDeporte);
    console.log('Relación encontrada:', relacion);

    if (!relacion) {
      return 'Desconocido';
    }

    // Encuentra la cancha correspondiente
    const cancha = this.cancha.find(c => c.idCancha === relacion.idCancha);
    console.log('Cancha encontrada:', cancha);
  
    return cancha ? cancha.nombreCancha : 'Desconocido';
  }

  crearReserva(modal: BsModalRef): void {
    if (this.createForm.invalid) return;
    this.reservasService.crearReserva(this.createForm.value).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Reserva creada correctamente', 'success');
        modal.hide();
        this.obtenerReservas();
      },
      error: () => {
        Swal.fire('Error', 'No se pudo crear la reserva', 'error');
      }
    });
  }

  openCreateModal(template: TemplateRef<any>): void {
    this.createForm.reset();
    this.modalRef = this.modalService.show(template);
  }

  editarReserva(reserva: ReservaDTO, template: TemplateRef<any>): void {
    this.selectedReservaId = reserva.idReserva;
    this.editForm.patchValue(reserva);
    this.modalRef = this.modalService.show(template);
  }

  actualizarReserva(modal: BsModalRef): void {
    if (this.editForm.invalid || this.selectedReservaId === undefined) return;
    this.reservasService.actualizarReserva(this.selectedReservaId, this.editForm.value).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Reserva actualizada correctamente', 'success');
        modal.hide();
        this.obtenerReservas();
      },
      error: () => {
        Swal.fire('Error', 'No se pudo actualizar la reserva', 'error');
      }
    });
  }

  eliminarReserva(idReserva: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.reservasService.eliminarReserva(idReserva).subscribe({
          next: () => {
            Swal.fire('Eliminado', 'Reserva eliminada correctamente', 'success');
            this.obtenerReservas();
          },
          error: () => {
            Swal.fire('Error', 'No se pudo eliminar la reserva', 'error');
          }
        });
      }
    });
  }
}