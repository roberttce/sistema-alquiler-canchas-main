import { CommonModule } from '@angular/common';
import { Component, TemplateRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteDTO } from '../../../models/cliente.dto';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ClienteService } from '../../../services/cliente.service';
import Swal from 'sweetalert2';
import { SidebarComponent } from "../../sidebar/sidebar.component";

@Component({
  selector: 'app-cliente-listar',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SidebarComponent
  ],
  templateUrl: './cliente-listar.component.html',
  styleUrls: ['./cliente-listar.component.scss']
})
export class ClienteListarComponent {
  clientes: ClienteDTO[] = [];
  loading: boolean = true;
  errorMessage: string = '';
  editForm: FormGroup;
  modalRef?: BsModalRef;
  frmCliente: FormGroup;
  typeResponse: string = '';
  listMessageResponse: string[] = [];

  constructor(
    private clienteService: ClienteService,
    private modalService: BsModalService,
    private fb: FormBuilder
  ) {
    this.editForm = this.fb.group({
      idCliente: [''],
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      correoElectronico: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      dni: ['', Validators.required],
      direccion: ['', Validators.required],
      fechaNacimiento: ['', Validators.required]
    });

    // Definir el formulario reactivo para crear clientes
    this.frmCliente = this.fb.group({
      nombre: ['', [Validators.required]],
      apellido: ['', [Validators.required]],
      correoElectronico: ['', [Validators.required, Validators.email]],
      telefono: ['', [Validators.required]],
      dni: ['', [Validators.required]],
      direccion: ['', [Validators.required]],
      fechaNacimiento: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.obtenerClientes();
  }

  obtenerClientes(): void {
    this.clienteService.listarClientes().subscribe({
      next: (response: any) => {
        if (response && response.data) {
          this.clientes = response.data;
          console.log('Clientes actualizados:', this.clientes);
        }
        this.loading = false;
      },
      error: (error: any) => {
        this.errorMessage = 'Ocurrió un error al cargar los clientes';
        console.error('Error al listar clientes:', error);
        this.loading = false;
      }
    });
  }

  // Eliminar cliente
  eliminarCliente(idCliente: number | undefined): void {
    if (idCliente === undefined) {
      console.error('ID de cliente no válido.');
      return;
    }

    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Una vez eliminado, no podrás recuperar este cliente!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.clienteService.eliminarCliente(idCliente).subscribe({
          next: () => {
            Swal.fire({
              position: "top-end",
              icon: "success",
              title: "Registro eliminado correctamente",
              showConfirmButton: false,
              timer: 1500,
              toast: true,
            });
            this.obtenerClientes();
          },
          error: (err) => {
            console.error('Error al eliminar el cliente:', err);
            Swal.fire('Error!', 'Error al intentar eliminar el cliente. Inténtalo nuevamente.', 'error');
          }
        });
      }
    });
  }

  // Método para actualizar cliente
  actualizarCliente(cliente: ClienteDTO, modal: BsModalRef): void {
    if (cliente.idCliente === undefined) {
      Swal.fire('Error!', 'ID de cliente no válido.', 'error');
      return;
    }

    this.clienteService.actualizarCliente(cliente.idCliente, cliente).subscribe({
      next: () => {
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Cliente actualizado exitosamente',
          showConfirmButton: false,
          timer: 1500,
          toast: true
        });
        this.obtenerClientes();
      },
      error: (error) => {
        console.error('Error al actualizar cliente:', error);
        Swal.fire('Error!', 'Error al intentar actualizar el cliente. Inténtalo nuevamente.', 'error');
      }
    });
  }

  // Método para abrir el modal de edición
  editarCliente(cliente: ClienteDTO, modal: TemplateRef<any>): void {
    this.editForm.patchValue(cliente);
    this.modalRef = this.modalService.show(modal);
  }

  // Método para abrir el modal de creación
  abrirModal(modal: TemplateRef<any>): void {
    this.frmCliente.reset(); // Asegurar que el formulario esté limpio antes de abrir
    this.modalRef = this.modalService.show(modal);
  }

  // Función para guardar el nuevo cliente
  public guardarCliente(): void {
    if (!this.frmCliente.valid) {
      this.frmCliente.markAllAsTouched();
      return;
    }

    const nuevoCliente: ClienteDTO = {
      nombre: this.nombreFb.value,
      apellido: this.apellidoFb.value,
      correoElectronico: this.correoElectronicoFb.value,
      telefono: this.telefonoFb.value,
      dni: this.dniFb.value,
      direccion: this.direccionFb.value,
      fechaNacimiento: this.fechaNacimientoFb.value
    };

    this.clienteService.crearCliente(nuevoCliente).subscribe({
      next: () => {
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Cliente registrado exitosamente',
          showConfirmButton: false,
          timer: 1500,
          toast: true
        });

        this.frmCliente.reset();
        this.frmCliente.markAsPristine();
        this.obtenerClientes();

        if (this.modalRef) {
          this.modalRef.hide();
        }
      },
      error: (error) => {
        console.error('Error al guardar cliente:', error);
        Swal.fire('Error!', 'Error al registrar el cliente. Inténtalo nuevamente.', 'error');
      }
    });
  }

  // Métodos de acceso para obtener los controles de cada campo
  get nombreFb() { return this.frmCliente.controls['nombre']; }
  get apellidoFb() { return this.frmCliente.controls['apellido']; }
  get correoElectronicoFb() { return this.frmCliente.controls['correoElectronico']; }
  get telefonoFb() { return this.frmCliente.controls['telefono']; }
  get dniFb() { return this.frmCliente.controls['dni']; }
  get direccionFb() { return this.frmCliente.controls['direccion']; }
  get fechaNacimientoFb() { return this.frmCliente.controls['fechaNacimiento']; }


  
}
