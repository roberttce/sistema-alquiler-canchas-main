 package com.example.backend_alquiler_canchas.dto;

import com.example.backend_alquiler_canchas.model.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Integer idReserva;
    
    private LocalDate fechaReserva;
    
    private LocalTime horaInicio;
    
    private BigDecimal costoTotal;
    
    private BigDecimal adelanto;
    
    private EstadoReserva estado; 
    
    private Integer idCliente;   
    
    private Integer idCanchaDeporte;   
}
