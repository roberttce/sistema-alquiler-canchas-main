package com.example.backend_alquiler_canchas.model;

public enum EstadoReserva {
    PENDIENTE,    // Reserva creada pero no pagada
    COMPLETADA,   // Reserva completamente pagad
    INCOMPLETO,   // Reserva confirmada, esperando acción (por ejemplo, pago o disponibilidad)
    EN_ESPERA     // Reserva en espera de algo (por ejemplo, esperando confirmación o pago)
}
