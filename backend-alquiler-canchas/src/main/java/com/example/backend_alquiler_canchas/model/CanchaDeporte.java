package com.example.backend_alquiler_canchas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cancha_deporte", uniqueConstraints = @UniqueConstraint(columnNames = {"idCancha", "idDeporte"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanchaDeporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCanchaDeporte;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idCancha", nullable = false, foreignKey = @ForeignKey(name = "fk_cancha"))
    private Cancha cancha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idDeporte", nullable = false, foreignKey = @ForeignKey(name = "fk_deporte"))
    private Deporte deporte;

    public Integer getCanchaIdCancha() {
        return cancha != null ? cancha.getIdCancha() : null;
    }
    public void setCanchaIdCancha(Integer idCancha) {
        if (this.cancha == null) {
            this.cancha = new Cancha();
        }
        this.cancha.setIdCancha(idCancha);
    }
}
