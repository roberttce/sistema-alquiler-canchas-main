import { Component, TemplateRef } from '@angular/core';
import { AdministradorService } from '../../../services/administrador.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { AdministradorCrearComponent } from '../administrador-crear/administrador-crear.component';
import { AdministradorDTO } from '../../../models/administrador.dto';
import Swal from 'sweetalert2';
import { SidebarComponent } from "../../sidebar/sidebar.component";

@Component({
  selector: 'app-administrador-listar',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SidebarComponent
],
  templateUrl: './administrador-listar.component.html',
  styleUrls: ['./administrador-listar.component.scss']
})
export class AdministradorListarComponent {
  administradores: any[] = [];
  loading: boolean = true;
  errorMessage: string = '';
  editForm: FormGroup;
  modalRef?: BsModalRef;

  constructor(
    private administradorService: AdministradorService,
    private modalService: BsModalService,
    private formBuilder: FormBuilder,
    private fb: FormBuilder
  ) {
    this.editForm = this.fb.group({
      idAdministrador: [''],
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      usuario: ['', Validators.required],
      correoElectronico: ['', [Validators.required, Validators.email]],
      contrasena: ['']
    });
    this.frmAdministrador = this.formBuilder.group({
      nombre: ['', [Validators.required]],
      apellido: ['', [Validators.required]],
      usuario: ['', [Validators.required]],
      correoElectronico: ['', [Validators.required, Validators.email]],
      contrasena: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    this.obtenerAdministradores();
  }

  obtenerAdministradores(): void {
    this.administradorService.listarAdministradores().subscribe({
      next: (response: any) => {
        console.log("Lista de administradores obtenida:", response);
        this.administradores = response.data;  // Actualizar lista
        this.loading = false;
      },
      error: (error: any) => {
        this.errorMessage = 'Ocurrió un error al cargar los administradores';
        console.error('Error al listar administradores:', error);
        this.loading = false;
      }
    });
  }
  

  eliminarAdministrador(idAdministrador: number | undefined): void {
      if (idAdministrador === undefined) {
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
              this.administradorService.eliminarAdministrador(idAdministrador).subscribe({
                  next: () => {
                      Swal.fire({position: "top-end",
                        icon: "success",
                        title: "Registro eliminado correctamente",
                        showConfirmButton: false,
                        timer: 1500,
                        toast: true, })// Duración en milisegundos);
                      this.obtenerAdministradores();
                  },
                  error: (err) => {
                      console.error('Error al eliminar el cliente:', err);
                      Swal.fire('Error!', 'Error al intentar eliminar el cliente. Inténtalo nuevamente.', 'error');
                  }
              });
          }
      });
  }
  

  //actualizar administrador
  actualizarAdministrador(Administrador: AdministradorDTO, modal: BsModalRef): void {
    if (Administrador.idAdministrador === undefined) { // Asegúrate de usar 'idCliente'
        Swal.fire('Error!', 'ID de cliente no válido.', 'error');
        return;
    }
  
    this.administradorService.actualizarAdministrador(Administrador.idAdministrador, Administrador).subscribe({
        next: (response) => {
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Cliente actualizado exitosamente',
                showConfirmButton: false,
                timer: 1500,
                toast: true
            });
            this.obtenerAdministradores(); // Refrescar la lista de clientes
            modal.hide(); // Cerrar el modal
        },
        error: (error) => {
            console.error('Error al actualizar cliente:', error);
            Swal.fire('Error!', 'Error al intentar actualizar el cliente. Inténtalo nuevamente.', 'error');
        }
    });
  }


  //editar administrador 
  editarAdministrador(Administrador: AdministradorDTO, modal: TemplateRef<any>): void {
      console.log(Administrador); // Verifica que el cliente tenga idCliente
      this.editForm.patchValue(Administrador); // Cargar datos del cliente en el formulario
      this.modalRef = this.modalService.show(modal); // Mostrar modal
    }

      // Método para abrir el modal de creación
      frmAdministrador: FormGroup;
      typeResponse: string = '';
      listMessageResponse: string[] = [];
    
      // Métodos de acceso para obtener los controles de cada campo
      get nombreFb() { return this.frmAdministrador.controls['nombre']; }
      get apellidoFb() { return this.frmAdministrador.controls['apellido']; }
      get usuarioFb() { return this.frmAdministrador.controls['usuario']; }
      get correoElectronicoFb() { return this.frmAdministrador.controls['correoElectronico']; }
      get contrasenaFb() { return this.frmAdministrador.controls['contrasena']; }
    
      // Función para guardar el nuevo administrador
      
      public guardarAdministrador(): void {
        if (!this.frmAdministrador.valid) {
          this.frmAdministrador.markAllAsTouched();
          return;
        }
      
        const nuevoAdministrador: AdministradorDTO = {
          nombre: this.nombreFb.value,
          apellido: this.apellidoFb.value,
          usuario: this.usuarioFb.value,
          correoElectronico: this.correoElectronicoFb.value,
          contrasena: this.contrasenaFb.value
        };
      
        this.administradorService.crearAdministrador(nuevoAdministrador).subscribe({
              next: () => {
                Swal.fire({
                  position: 'top-end',
                  icon: 'success',
                  title: 'Cliente registrado exitosamente',
                  showConfirmButton: false,
                  timer: 1500,
                  toast: true
                });
        
                this.frmAdministrador.reset();
                this.frmAdministrador.markAsPristine();
                this.obtenerAdministradores();
        
                if (this.modalRef) {
                  this.modalRef.hide();
                }
              },
              error: (error: any) => {
                console.error('Error al guardar cliente:', error);
                Swal.fire('Error!', 'Error al registrar el cliente. Inténtalo nuevamente.', 'error');
              }
            });
      }
      
      
      

        // Método para abrir el modal de creación
  abrirModal(modal: TemplateRef<any>): void {
    this.frmAdministrador.reset(); // Asegurar que el formulario esté limpio antes de abrir
    this.modalRef = this.modalService.show(modal);
  }
 
}
