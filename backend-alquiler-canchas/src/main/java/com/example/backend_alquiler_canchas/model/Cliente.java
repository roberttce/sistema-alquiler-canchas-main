package com.example.backend_alquiler_canchas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres.")
    private String apellido;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debe proporcionar un correo electrónico válido.")
    @Column(unique = true)
    private String correoElectronico;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Size(max = 15, message = "El teléfono no puede exceder los 15 caracteres.")
    private String telefono;

    @NotBlank(message = "El DNI es obligatorio.")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres.")
    @Column(unique = true)
    private String dni;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;
    
    @UpdateTimestamp
    private LocalDateTime fechaModificacion;
}
