 package com.example.backend_alquiler_canchas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemanaDTO {
    private Integer idCancha;
    private String nombreCancha;
    private List<HorarioDTO> horarios;
}
