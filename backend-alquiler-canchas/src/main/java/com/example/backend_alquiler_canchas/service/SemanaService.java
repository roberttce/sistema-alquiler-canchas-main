 package com.example.backend_alquiler_canchas.service;

import com.example.backend_alquiler_canchas.dto.HorarioDTO;
import com.example.backend_alquiler_canchas.dto.SemanaDTO;
import com.example.backend_alquiler_canchas.model.Cancha;
import com.example.backend_alquiler_canchas.model.Reserva;
import com.example.backend_alquiler_canchas.repository.CanchaRepository;
import com.example.backend_alquiler_canchas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class SemanaService {

    @Autowired
    private CanchaRepository canchaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    private final String[] horarios = {
            "08:00 AM - 09:00 AM", "09:00 AM - 10:00 AM", "10:00 AM - 11:00 AM",
            "11:00 AM - 12:00 PM", "12:00 PM - 01:00 PM", "01:00 PM - 02:00 PM",
            "02:00 PM - 03:00 PM", "03:00 PM - 04:00 PM", "04:00 PM - 05:00 PM",
            "05:00 PM - 06:00 PM", "06:00 PM - 07:00 PM", "07:00 PM - 08:00 PM",
            "08:00 PM - 09:00 PM", "09:00 PM - 10:00 PM", "10:00 PM - 11:00 PM"
    };

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

    /**
     * Obtiene la información de las reservas de una semana.
     * @param offset Número de semanas de diferencia respecto a la semana actual (0 = actual, 1 = siguiente, -1 = anterior)
     * @return Lista de SemanaDTO con la información de cada cancha.
     */
    public List<SemanaDTO> obtenerSemana(int offset) {
        List<SemanaDTO> resultado = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        // Obtener el lunes de la semana actual y luego aplicar el offset de semanas
        LocalDate lunesActual = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lunes = lunesActual.plusWeeks(offset);
        LocalDate domingo = lunes.plusDays(6);

        // Obtener las reservas de la semana indicada
        List<Reserva> reservasSemana = reservaRepository.findByFechaReservaBetween(lunes, domingo);
        List<Cancha> canchas = canchaRepository.findAll();

        for (Cancha cancha : canchas) {
            List<Reserva> reservasCancha = reservasSemana.stream()
                    .filter(r -> r.getCanchaDeporte() != null &&
                                 r.getCanchaDeporte().getCancha() != null &&
                                 r.getCanchaDeporte().getCancha().getIdCancha().equals(cancha.getIdCancha()))
                    .collect(Collectors.toList());

            List<HorarioDTO> listaHorarios = new ArrayList<>();

            for (String bloque : horarios) {
                String horaInicioStr = bloque.split(" - ")[0];
                LocalTime horaInicio = LocalTime.parse(horaInicioStr, formatter);

          
                HorarioDTO horarioDTO = new HorarioDTO(bloque, "disponible", "disponible", "disponible", "disponible", "disponible", "disponible", "disponible");

                for (int i = 0; i < 7; i++) {
                    LocalDate fecha = lunes.plusDays(i);
                    Reserva reservaEncontrada = reservasCancha.stream()
                            .filter(r -> r.getFechaReserva().equals(fecha) && r.getHoraInicio().equals(horaInicio))
                            .findFirst()
                            .orElse(null);

                    String estado = (reservaEncontrada != null) ? reservaEncontrada.getEstado().toString() : "disponible";

                    switch (i) {
                        case 0: horarioDTO.setLunes(estado); break;
                        case 1: horarioDTO.setMartes(estado); break;
                        case 2: horarioDTO.setMiercoles(estado); break;
                        case 3: horarioDTO.setJueves(estado); break;
                        case 4: horarioDTO.setViernes(estado); break;
                        case 5: horarioDTO.setSabado(estado); break;
                        case 6: horarioDTO.setDomingo(estado); break;
                    }
                }

                listaHorarios.add(horarioDTO);
            }

            SemanaDTO dto = new SemanaDTO(cancha.getIdCancha(), cancha.getNombreCancha(), listaHorarios);
            resultado.add(dto);
        }

        return resultado;
    }
}
