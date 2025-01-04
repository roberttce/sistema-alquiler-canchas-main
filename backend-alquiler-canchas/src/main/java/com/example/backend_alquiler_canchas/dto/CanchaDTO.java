package com.example.backend_alquiler_canchas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanchaDTO {

    private Integer idCancha;

    @NotBlank(message = "El nombre de la cancha es obligatorio.")
    @Size(max = 50, message = "El nombre de la cancha no puede exceder los 50 caracteres.")
    private String nombreCancha;

    @NotBlank(message = "El estado es obligatorio.")
    @Pattern(regexp = "disponible|reservada|mantenimiento", message = "Estado inválido.")
    private String estado;
}
