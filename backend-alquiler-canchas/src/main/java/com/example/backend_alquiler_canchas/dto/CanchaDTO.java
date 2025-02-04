package com.example.backend_alquiler_canchas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanchaDTO {

    private Integer idCancha;

    @NotBlank(message = "El nombre de la cancha es obligatorio.")
    @Size(max = 50, message = "El nombre de la cancha no puede exceder los 50 caracteres.")
    private String nombreCancha;

    @NotNull(message = "El costo por hora es obligatorio.")
    @Digits(integer = 10, fraction = 2, message = "El formato del costo debe ser válido.")
    private BigDecimal costoPorHora;
    
    private String estado;
}
