package com.example.backend_alquiler_canchas.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponse<T> {
    private boolean exito;
    private String mensaje;
    private T data; 
}
