package com.example.backend_alquiler_canchas.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeporteDTO {
    private Integer idDeporte;

    @NotBlank(message = "El nombre del deporte es obligatorio.")
    @Size(max = 50, message = "El nombre del deporte no puede exceder los 50 caracteres.")
    private String nombreDeporte;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres.")
    private String descripcion;
}
