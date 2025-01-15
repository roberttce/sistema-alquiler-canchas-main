package com.example.backend_alquiler_canchas.service;

import com.example.backend_alquiler_canchas.dto.ReservaDTO;
import com.example.backend_alquiler_canchas.model.CanchaDeporte;
import com.example.backend_alquiler_canchas.model.Cliente;
import com.example.backend_alquiler_canchas.model.EstadoReserva;
import com.example.backend_alquiler_canchas.model.Reserva;
import com.example.backend_alquiler_canchas.repository.CanchaDeporteRepository;
import com.example.backend_alquiler_canchas.repository.ClienteRepository;
import com.example.backend_alquiler_canchas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.time.Duration;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final CanchaDeporteRepository canchaDeporteRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, ClienteRepository clienteRepository, CanchaDeporteRepository canchaDeporteRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.canchaDeporteRepository = canchaDeporteRepository;
    }

    public List<ReservaDTO> listarReservas() {
        return reservaRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
        Cliente cliente = clienteRepository.findById(reservaDTO.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        CanchaDeporte canchaDeporte = canchaDeporteRepository.findById(reservaDTO.getIdCanchaDeporte())
                .orElseThrow(() -> new IllegalArgumentException("CanchaDeporte no encontrado"));

        //BigDecimal costoPorHora = canchaDeporte.getCancha().getCostoPorHora();
        BigDecimal costoTotal = canchaDeporte.getCancha().getCostoPorHora();

        Reserva reserva = mapearADominio(reservaDTO, cliente, canchaDeporte);
        reserva.setCostoTotal(costoTotal);

        reserva.setEstado(calcularEstadoReserva(reserva.getAdelanto(), reserva.getCostoTotal()));

        reserva = reservaRepository.save(reserva);
        return mapearADTO(reserva);
    }

    @Transactional
    public ReservaDTO actualizarReserva(Integer idReserva, ReservaDTO reservaDTO) {
        Reserva reservaExistente = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        Cliente cliente = clienteRepository.findById(reservaDTO.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        CanchaDeporte canchaDeporte = canchaDeporteRepository.findById(reservaDTO.getIdCanchaDeporte())
                .orElseThrow(() -> new IllegalArgumentException("CanchaDeporte no encontrado"));
        BigDecimal costoPorHora = canchaDeporte.getCancha().getCostoPorHora();
        BigDecimal costoTotal = canchaDeporte.getCancha().getCostoPorHora();

        reservaExistente.setFechaReserva(reservaDTO.getFechaReserva());
        reservaExistente.setHoraInicio(reservaDTO.getHoraInicio());
        reservaExistente.setCostoTotal(costoTotal);
        reservaExistente.setAdelanto(reservaDTO.getAdelanto());
        reservaExistente.setEstado(calcularEstadoReserva(reservaExistente.getAdelanto(), reservaExistente.getCostoTotal()));

        reservaExistente.setCliente(cliente);
        reservaExistente.setCanchaDeporte(canchaDeporte);

        reservaExistente = reservaRepository.save(reservaExistente);
        return mapearADTO(reservaExistente);
    }

    public List<ReservaDTO> obtenerReservasPorEstado(EstadoReserva estado) {
        return reservaRepository.findByEstado(estado).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public ReservaDTO obtenerReservaPorId(Integer idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        return mapearADTO(reserva);
    }

    public List<ReservaDTO> obtenerReservasPorCliente(Integer idCliente) {
        return reservaRepository.findByCliente_IdCliente(idCliente).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }
    
    public void eliminarReserva(Integer idReserva) {
        if (!reservaRepository.existsById(idReserva)) {
            throw new IllegalArgumentException("Reserva no encontrada");
        }
        reservaRepository.deleteById(idReserva);
    }

    public ReservaDTO confirmarPagoTotal(Integer idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        reserva.setEstado(EstadoReserva.COMPLETADA);
        reserva = reservaRepository.save(reserva);

        return mapearADTO(reserva);
    }
    private BigDecimal calcularCostoTotal(java.time.LocalTime horaInicio, java.time.LocalTime horaFin, BigDecimal costoPorHora) {
        long horas = Duration.between(horaInicio, horaFin).toHours();
        if (horas <= 0) {
            throw new IllegalArgumentException("La hora de fin debe ser mayor que la hora de inicio");
        }
        return costoPorHora.multiply(BigDecimal.valueOf(horas));
    }

    private EstadoReserva calcularEstadoReserva(BigDecimal adelanto, BigDecimal costoTotal) {
        if (adelanto.compareTo(costoTotal) >= 0) {
            return EstadoReserva.COMPLETADA;
        } else if (adelanto.compareTo(BigDecimal.ZERO) > 0) {
            return EstadoReserva.PENDIENTE;
        }
        return EstadoReserva.PENDIENTE;
    }

    private Reserva mapearADominio(ReservaDTO reservaDTO, Cliente cliente, CanchaDeporte canchaDeporte) {
        return Reserva.builder()
                .fechaReserva(reservaDTO.getFechaReserva())
                .horaInicio(reservaDTO.getHoraInicio())
                .costoTotal(reservaDTO.getCostoTotal())
                .adelanto(reservaDTO.getAdelanto())
                .estado(reservaDTO.getEstado())
                .cliente(cliente)
                .canchaDeporte(canchaDeporte)
                .build();
    }

    private ReservaDTO mapearADTO(Reserva reserva) {
        return new ReservaDTO(
                reserva.getIdReserva(),
                reserva.getFechaReserva(),
                reserva.getHoraInicio(), 
                reserva.getCostoTotal(),
                reserva.getAdelanto(),
                reserva.getEstado(),
                reserva.getCliente().getIdCliente(),
                reserva.getCanchaDeporte().getIdCanchaDeporte()
        );
    }
}
