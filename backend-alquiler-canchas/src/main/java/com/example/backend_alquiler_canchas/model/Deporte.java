package com.example.backend_alquiler_canchas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDeporte;

    @NotBlank(message = "El nombre del deporte es obligatorio.")
    @Size(max = 50, message = "El nombre del deporte no puede exceder los 50 caracteres.")
    private String nombreDeporte;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres.")
    private String descripcion;

    @NotNull(message = "El costo por hora es obligatorio.")
    @Digits(integer = 10, fraction = 2, message = "El formato del costo debe ser válido.")
    private BigDecimal costoPorHora;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaModificacion;
}
