// Ubicación: src/main/java/com/example/backend_alquiler_canchas/dto/LoginRequestDTO.java

package com.example.backend_alquiler_canchas.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    private String usuario;
    private String contrasena;
}
