package com.example.backend_alquiler_canchas.repository;

import com.example.backend_alquiler_canchas.model.Reserva;
import com.example.backend_alquiler_canchas.model.EstadoReserva;  
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByEstado(EstadoReserva estado);
    List<Reserva> findByCliente_IdCliente(Integer idCliente);
}

