package com.example.backend_alquiler_canchas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cancha")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCancha;

    @NotBlank(message = "El nombre de la cancha es obligatorio.")
    @Size(max = 50, message = "El nombre de la cancha no puede exceder los 50 caracteres.")
    @Column(unique = true)
    private String nombreCancha;

    @NotBlank(message = "El estado es obligatorio.")
    @Pattern(regexp = "disponible|reservada|mantenimiento", message = "Estado inválido.")
    private String estado;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaModificacion;
}
