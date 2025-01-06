package com.example.backend_alquiler_canchas.controller;

import com.example.backend_alquiler_canchas.dto.ReservaDTO;
import com.example.backend_alquiler_canchas.service.ReservaService;
import com.example.backend_alquiler_canchas.util.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend_alquiler_canchas.model.EstadoReserva;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<ReservaDTO>> crearReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        ReservaDTO reservaCreada = reservaService.crearReserva(reservaDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Reserva creada exitosamente", reservaCreada));
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<ReservaDTO>>> listarReservas() {
        List<ReservaDTO> reservas = reservaService.listarReservas();
        return ResponseEntity.ok(new GlobalResponse<>(true, "Lista de reservas obtenida exitosamente", reservas));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<GlobalResponse<List<ReservaDTO>>> obtenerReservasPorEstado(@PathVariable EstadoReserva estado) {
        List<ReservaDTO> reservas = reservaService.obtenerReservasPorEstado(estado);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Reservas encontradas con el estado " + estado, reservas));
    }

    @GetMapping("/{idReserva}")
    public ResponseEntity<GlobalResponse<ReservaDTO>> obtenerReservaPorId(@PathVariable Integer idReserva) {
        ReservaDTO reserva = reservaService.obtenerReservaPorId(idReserva);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Reserva encontrada", reserva));
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<GlobalResponse<List<ReservaDTO>>> obtenerReservasPorCliente(@PathVariable Integer idCliente) {
        List<ReservaDTO> reservas = reservaService.obtenerReservasPorCliente(idCliente);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Reservas encontradas para el cliente con ID: " + idCliente, reservas));
    }

    @PutMapping("/{idReserva}")
    public ResponseEntity<GlobalResponse<ReservaDTO>> actualizarReserva(@PathVariable Integer idReserva, @Valid @RequestBody ReservaDTO reservaDTO) {
        ReservaDTO reservaActualizada = reservaService.actualizarReserva(idReserva, reservaDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Reserva actualizada exitosamente", reservaActualizada));
    }

    @DeleteMapping("/{idReserva}")
    public ResponseEntity<GlobalResponse<Void>> eliminarReserva(@PathVariable Integer idReserva) {
        reservaService.eliminarReserva(idReserva);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Reserva eliminada exitosamente", null));
    }

    @PostMapping("/{idReserva}/confirmarPago")
    public ResponseEntity<GlobalResponse<ReservaDTO>> confirmarPagoTotal(@PathVariable Integer idReserva) {
        ReservaDTO reservaConfirmada = reservaService.confirmarPagoTotal(idReserva);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Pago total confirmado para la reserva", reservaConfirmada));
    }
}
