package com.example.backend_alquiler_canchas.model;

public enum EstadoReserva {
    PENDIENTE,    // Reserva creada pero no pagada
    COMPLETADA,   // Reserva completamente pagad
    INCOMPLETO,   // Reserva confirmada, esperando acción cuando el pago es igual a 0)
}
