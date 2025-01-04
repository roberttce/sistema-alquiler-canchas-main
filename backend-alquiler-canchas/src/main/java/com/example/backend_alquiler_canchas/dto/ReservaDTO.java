package com.example.backend_alquiler_canchas.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private String fechaReserva;
    private String horaInicio;
    private String horaFin;
    private Double costoTotal;
    private Integer idCliente;
    private Integer idCanchaDeporte;
    private boolean estado;
}
