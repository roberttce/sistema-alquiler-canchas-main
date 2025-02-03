export interface ReservaDTO {
  idReserva?: number;
  fechaReserva: string; // formato 'YYYY-MM-DD'
  horaInicio: string;   // formato 'HH:mm:ss'
  costoTotal: number;
  adelanto: number;
  idCliente: number;
  idCanchaDeporte: number;
  estado: 'PENDIENTE' | 'COMPLETADO' | 'INCOMPLETO';
}
