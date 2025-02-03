import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdministradorDTO } from '../../../models/administrador.dto';
import { AdministradorService } from '../../../services/administrador.service';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BrowserModule } from '@angular/platform-browser';
import { SidebarComponent } from "../../sidebar/sidebar.component";

@Component({
  selector: 'app-administrador-component',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SidebarComponent
],
  providers: [BsModalService],  // Agregar el servicio de modal
  templateUrl: './administrador-crear.component.html',
  styleUrl: './administrador-crear.component.scss'
})

export class AdministradorCrearComponent {
  frmAdministrador: FormGroup;
  typeResponse: string = '';
  listMessageResponse: string[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private administradorService: AdministradorService
  ) {
    // Definir el formulario reactivo
    this.frmAdministrador = this.formBuilder.group({
      nombre: ['', [Validators.required]],
      apellido: ['', [Validators.required]],
      usuario: ['', [Validators.required]],
      correoElectronico: ['', [Validators.required, Validators.email]],
      contrasena: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  // Métodos de acceso para obtener los controles de cada campo
  get nombreFb() { return this.frmAdministrador.controls['nombre']; }
  get apellidoFb() { return this.frmAdministrador.controls['apellido']; }
  get usuarioFb() { return this.frmAdministrador.controls['usuario']; }
  get correoElectronicoFb() { return this.frmAdministrador.controls['correoElectronico']; }
  get contrasenaFb() { return this.frmAdministrador.controls['contrasena']; }

  // Función para guardar el nuevo administrador
  public guardarAdministrador(): void {
    // Validar formulario antes de proceder
    if (!this.frmAdministrador.valid) {
      this.frmAdministrador.markAllAsTouched();  // Marca todos los controles como tocados
      return;  // Detener la ejecución si el formulario no es válido
    }

    // Crear el objeto administrador con los valores del formulario
    const nuevoAdministrador: AdministradorDTO = {
      nombre: this.nombreFb.value,
      apellido: this.apellidoFb.value,
      usuario: this.usuarioFb.value,
      correoElectronico: this.correoElectronicoFb.value,
      contrasena: this.contrasenaFb.value
    };

    // Llamar al servicio para insertar el administrador
    this.administradorService.crearAdministrador(nuevoAdministrador).subscribe({
      next: (response: any) => {
        this.typeResponse = response.type;
        this.listMessageResponse = response.listMessage;

        if (response.type === 'success') {
          // Limpiar formulario si es exitoso
          
          this.frmAdministrador.reset();

        }
      },
      error: (error: any) => {
        console.error('Error al guardar administrador:', error);
        // Manejar el error si es necesario
      }
    });
  }

}
