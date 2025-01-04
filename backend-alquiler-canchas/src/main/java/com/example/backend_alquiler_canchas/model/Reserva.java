package com.example.backend_alquiler_canchas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReserva;

    @NotNull(message = "La fecha de la reserva es obligatoria.")
    private LocalDate fechaReserva;

    @NotNull(message = "La hora de inicio es obligatoria.")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria.")
    private LocalTime horaFin;

    @NotNull(message = "El costo total es obligatorio.")
    @Digits(integer = 10, fraction = 2, message = "El formato del costo debe ser válido.")
    private BigDecimal costoTotal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idCliente", nullable = false, foreignKey = @ForeignKey(name = "fk_cliente"))
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idCanchaDeporte", nullable = false, foreignKey = @ForeignKey(name = "fk_cancha_deporte"))
    private CanchaDeporte canchaDeporte;

    @NotNull(message = "El estado de la reserva es obligatorio.")
    private Boolean estado;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaModificacion;
}
