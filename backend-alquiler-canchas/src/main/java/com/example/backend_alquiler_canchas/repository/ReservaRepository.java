 package com.example.backend_alquiler_canchas.repository;

import com.example.backend_alquiler_canchas.model.Reserva;
import com.example.backend_alquiler_canchas.model.Cancha;
import com.example.backend_alquiler_canchas.model.CanchaDeporte;
import com.example.backend_alquiler_canchas.model.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.time.LocalDate; 

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByEstado(EstadoReserva estado);

    List<Reserva> findByCliente_IdCliente(Integer idCliente);
    boolean existsByFechaReservaAndHoraInicioAndCanchaDeporte_Cancha_IdCancha(LocalDate fechaReserva, LocalTime horaInicio, Integer idCancha);

    boolean existsByFechaReservaAndHoraInicioAndCanchaDeporte_Cancha_IdCanchaAndIdReservaNot(
        LocalDate fechaReserva, LocalTime horaInicio, Integer idCancha, Integer idReserva
    );
    int countByCliente_IdClienteAndEstado(Integer idCliente, EstadoReserva estado);
    void deleteByCliente_IdCliente(Integer idCliente);
    List<Reserva> findByFechaReservaBetweenAndCanchaDeporte(LocalDate inicio, LocalDate fin, CanchaDeporte canchaDeporte);
    List<Reserva> findByFechaReservaBetween(LocalDate inicio, LocalDate fin);
}
