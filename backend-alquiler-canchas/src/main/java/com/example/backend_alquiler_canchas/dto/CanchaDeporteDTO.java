 package com.example.backend_alquiler_canchas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanchaDeporteDTO {

    private Integer idCanchaDeporte;

    @NotNull(message = "El ID de la cancha es obligatorio.")
    private Integer idCancha;

    @NotNull(message = "El ID del deporte es obligatorio.")
    private Integer idDeporte;
}
