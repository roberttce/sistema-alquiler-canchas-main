import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { ReservasService } from '../../../services/reserva.service';
import { CanchadeporteService } from '../../../services/canchadeporte.service';
import { FormsModule } from '@angular/forms';
import { ReservaDTO } from '../../../models/reserva.dto';
import { ClienteService } from '../../../services/cliente.service';
import { SidebarComponent } from "../../sidebar/sidebar.component";
import { CanchasService } from '../../../services/canchas.service';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [CommonModule, FormsModule, SidebarComponent],
  providers: [BsModalService],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.scss']
})
export class InicioComponent {
  estadoCeldas: any = {};
  mostrarFormulario = false;
  canchaSeleccionada!: number;
  filaSeleccionada!: number;
  columnaSeleccionada!: number;
  fechaSeleccionada!: string;
  horaSeleccionada!: string;
  horarios = [
    '08:00 - 09:00', '09:00 - 10:00', '10:00 - 11:00', 
    '11:00 - 12:00', '12:00 - 13:00', '13:00 - 14:00', 
    '14:00 - 15:00', '15:00 - 16:00', '16:00 - 17:00', 
    '17:00 - 18:00', '18:00 - 19:00', '19:00 - 20:00'
  ];
  reserva = {
    dni: ``,
    nombreCliente: '',
    apellidoCliente: '',
    telefonoCliente: '',
    idCliente: 0,
    adelanto: 0
  };
  numSemanas: number = 4;
  fechasSemana: Date[] = [];
  canchasUnicas: any[] = [];
  canchas: any[] = [];
  clientes: any[] = [];
  reservas: any[] = [];
  loading: boolean = true;
  errorMessage: string = '';

  constructor(private reservasService: ReservasService,
    private canchaDeporteService: CanchadeporteService,
    private clienteService: ClienteService,
    private canchaService: CanchasService
  ) {
  }

  ngOnInit() {
    this.updateDateRange();
    this.listarcanchas();
    this.listarReservas();
    this.ObtenerrClientes()
  }

  updateDateRange() {
    const today = new Date();
    this.fechasSemana = [];
    
    for (let i = 0; i < this.numSemanas * 7; i++) {
      const newDate = new Date();
      newDate.setDate(today.getDate() + i);
      this.fechasSemana.push(newDate);
    }

    this.fetchCanchasDeportes(); // Primero carga las canchas, luego las reservas
  }

  listarcanchas(){
    this.canchaService.listarCanchas().subscribe(response => {
      console.log('canchas reciibidas:', response);
      this.canchas = response.data
    })
  }

  ObtenerrClientes(){
    this.clienteService.listarClientes().subscribe(response => {
      console.log('clientes recibidos:', response);
      this.clientes = response.data
      })
  }

  listarReservas(){
    this.reservasService.listarReservas().subscribe(response => {
      console.log('reservas reciibidas:', response);
      this.reservas = response.data;
      this.inicializarEstadoCeldas();
    })
  }

  getnombrecancha(idCancha: number): string {
    const cancha = this.canchas.find(c => c.idCancha === idCancha);
    return cancha ? cancha.nombreCancha : 'Desconocido';
  }

  getnombreCliente(idCliente: number): string {
    const cliente = this.clientes.find(c => c.idCliente === idCliente);
    return cliente ? `${cliente.nombre} ${cliente.apellido}` : 'Desconocido';
  }

  fetchCanchasDeportes() {
    this.canchaDeporteService.listarCanchasDeportes().subscribe(response => {
      this.canchasUnicas = response.data.map(cancha => ({
        idCanchaDeporte: cancha.idCanchaDeporte,
        idCancha: cancha.idCancha,
        idDeporte: cancha.idDeporte,
      }));

      this.inicializarEstadoCeldas();
    });
  }

  inicializarEstadoCeldas() { // Resetear estructura
    this.canchasUnicas.forEach(cancha => {
      this.estadoCeldas[cancha.idCancha] = this.horarios.map(() =>
        new Array(this.fechasSemana.length).fill(null).map(() => ({
          idReserva: null,
          nombre: '',
          adelanto: 0,
          precio: 0
        }))
      );
    });

    // Asignar las reservas a las celdas correspondientes
    this.reservas.forEach(reserva => {
      const { idCanchaDeporte, fechaReserva, horaInicio, adelanto, costoTotal, idReserva, idCliente } = reserva;

      // Buscar la cancha correspondiente
      const canchaEncontrada = this.canchasUnicas.find(c => c.idCanchaDeporte === idCanchaDeporte);
      if (!canchaEncontrada) return;

      const idCancha = canchaEncontrada.idCancha;

      // Encontrar la fecha y la hora en la tabla
      const fechaIndex = this.fechasSemana.findIndex(f => f.toISOString().split('T')[0] === fechaReserva);
      const horaIndex = this.horarios.findIndex(h => h.startsWith(horaInicio.substring(0, 5))); // Comparar HH:mm

      if (fechaIndex !== -1 && horaIndex !== -1) {
        this.estadoCeldas[idCancha][horaIndex][fechaIndex] = {
          idReserva: idReserva,
          nombre: this.getnombreCliente(idCliente),
          adelanto: adelanto,
          precio: costoTotal
        };
      }
    });
  }

  abrirFormulario(canchaDeporteId: number, fila: number, columna: number, fecha: string, hora: string) {
    this.canchaSeleccionada = canchaDeporteId;
    this.filaSeleccionada = fila;
    this.columnaSeleccionada = columna;
    this.fechaSeleccionada = fecha;
    this.horaSeleccionada = hora;
    this.mostrarFormulario = true;
  }

  cerrarFormulario() {
    this.mostrarFormulario = false;
  }

  buscarClientePorDNI() {
    this.clienteService.obtenerClientePorDNI(this.reserva.dni).subscribe(cliente => {
      if (cliente) {
        this.reserva.idCliente = cliente.idCliente;
        this.reserva.nombreCliente = cliente.nombre;
        this.reserva.apellidoCliente = cliente.apellido;
        this.reserva.telefonoCliente = cliente.telefono;
      } else {
        alert('Cliente no encontrado');
      }
    }, error => {
      console.error('Error al buscar cliente: ', error);
      alert('Error al buscar cliente');
    });
  }

  guardarReserva() {
    if (this.reserva.idCliente === 0) {
      alert('Debes ingresar un DNI válido y buscar el cliente.');
      return;
    }

    const nuevaReserva: ReservaDTO = {
      fechaReserva: this.fechaSeleccionada,
      horaInicio: this.horaSeleccionada.split(' - ')[0],
      costoTotal: 0,
      adelanto: this.reserva.adelanto,
      idCliente: this.reserva.idCliente,
      idCanchaDeporte: this.canchaSeleccionada,
      estado: 'PENDIENTE',
    };

    this.reservasService.crearReserva(nuevaReserva).subscribe(() => {
      this.cerrarFormulario();
      this.listarReservas();
    }, error => {
      console.error('Error al guardar la reserva:', error);
    });
  }

  editarReserva(canchaDeporteId: number, fila: number, columna: number) {
    const cancha = this.canchasUnicas.find(c => c.idCanchaDeporte === canchaDeporteId);
    if (!cancha) return;

    const reservaExistente = this.estadoCeldas[cancha.idCancha]?.[fila]?.[columna];

    if (reservaExistente && reservaExistente.idReserva) {
      this.reserva = {
        dni: '',
        nombreCliente: reservaExistente.nombre,
        apellidoCliente: reservaExistente.apellido,
        telefonoCliente: reservaExistente.telefono,
        idCliente: reservaExistente.idReserva,
        adelanto: reservaExistente.adelanto
      };

      this.canchaSeleccionada = canchaDeporteId;
      this.filaSeleccionada = fila;
      this.columnaSeleccionada = columna;
      this.fechaSeleccionada = this.fechasSemana[columna].toISOString().split('T')[0];
      this.horaSeleccionada = this.horarios[fila];
    } else {
      alert('No hay reserva en esta celda para editar.');
    }
  }
}
