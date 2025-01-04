package com.example.backend_alquiler_canchas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "administrador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAdministrador;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(max = 100, message = "El apellido no puede exceder los 100 caracteres.")
    private String apellido;

    @NotBlank(message = "El usuario es obligatorio.")
    @Size(max = 50, message = "El usuario no puede exceder los 50 caracteres.")
    @Column(unique = true)
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String contrasena;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debe proporcionar un correo electrónico válido.")
    @Column(unique = true)
    private String correoElectronico;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaModificacion;
}
