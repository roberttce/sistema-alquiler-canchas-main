package com.example.backend_alquiler_canchas.model;

public enum EstadoReserva {
    PENDIENTE,     
    COMPLETADA,    
    INCOMPLETO,   // Reserva confirmada, esperando acción cuando el pago es igual a 0)
}
