import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteDTO } from '../../../models/cliente.dto';  // Cambiar al modelo de Cliente
import { ClienteService } from '../../../services/cliente.service';  // Cambiar al servicio de Cliente
import { BsModalService } from 'ngx-bootstrap/modal';
import { SidebarComponent } from "../../sidebar/sidebar.component";

@Component({
  selector: 'app-cliente-crear',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SidebarComponent
],
  providers: [BsModalService],  // Agregar el servicio de modal
  templateUrl: './cliente-crear.component.html',  // Cambiar al nombre del archivo HTML de Cliente
  styleUrl: './cliente-crear.component.scss'
})

export class ClienteCrearComponent {
  frmCliente: FormGroup;
  typeResponse: string = '';
  listMessageResponse: string[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private clienteService: ClienteService
  ) {
    // Definir el formulario reactivo
    this.frmCliente = this.formBuilder.group({
      nombre: ['', [Validators.required]],
      apellido: ['', [Validators.required]],
      correoElectronico: ['', [Validators.required, Validators.email]],
      telefono: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],  // Añadir teléfono
      dni: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]],  // Añadir DNI
      direccion: ['', [Validators.required]],
      fechaNacimiento: ['', [Validators.required, this.fechaNoFutura]],
    
    });
  }


  // Métodos de acceso para obtener los controles de cada campo
  get nombreFb() { return this.frmCliente.controls['nombre']; }
  get apellidoFb() { return this.frmCliente.controls['apellido']; }
  get correoElectronicoFb() { return this.frmCliente.controls['correoElectronico']; }
  get telefonoFb() { return this.frmCliente.controls['telefono']; }  // Añadir teléfono
  get dniFb() { return this.frmCliente.controls['dni']; }  // Añadir DNI
  get direccionFb() { return this.frmCliente.controls['direccion']; }
  get fechaNacimientoFb() { return this.frmCliente.controls['fechaNacimiento']; }  // Añadir teléfono
  
   // Validación personalizada para evitar fechas futuras
   fechaNoFutura(control: any) {
    if (!control.value) return null; // Si el campo está vacío, no aplicar validación

    const fechaIngresada = new Date(control.value);
    const fechaActual = new Date();

    return fechaIngresada > fechaActual ? { fechaFutura: true } : null;
  }
  

  // Función para guardar el nuevo cliente
  public guardarCliente(): void {
    // Validar formulario antes de proceder
    if (!this.frmCliente.valid) {
      this.frmCliente.markAllAsTouched();  // Marca todos los controles como tocados
      return;  // Detener la ejecución si el formulario no es válido
    }

    // Crear el objeto cliente con los valores del formulario
    const nuevoCliente: ClienteDTO = {
      nombre: this.nombreFb.value,
      apellido: this.apellidoFb.value,
      correoElectronico: this.correoElectronicoFb.value,
      telefono: this.telefonoFb.value, // Añadir teléfono
      dni: this.dniFb.value, // Añadir DNI
      direccion: this.direccionFb.value,
      fechaNacimiento: this.fechaNacimientoFb.value
    };

    // Llamar al servicio para insertar el cliente
    this.clienteService.crearCliente(nuevoCliente).subscribe({
      next: (response: any) => {
        this.typeResponse = response.type;
        this.listMessageResponse = response.listMessage;

        if (response.type === 'success') {
          // Limpiar formulario si es exitoso
          this.frmCliente.reset();
        }
      },
      error: (error: any) => {
        console.error('Error al guardar cliente:', error);
        // Manejar el error si es necesario
      }
    });
  }

}
