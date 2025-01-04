package com.example.backend_alquiler_canchas.service;

import com.example.backend_alquiler_canchas.dto.DeporteDTO;
import com.example.backend_alquiler_canchas.model.Deporte;
import com.example.backend_alquiler_canchas.repository.DeporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeporteService {

    private final DeporteRepository deporteRepository;

    @Autowired
    public DeporteService(DeporteRepository deporteRepository) {
        this.deporteRepository = deporteRepository;
    }

    public DeporteDTO crearDeporte(DeporteDTO deporteDTO) {
        Deporte deporte = mapearADominio(deporteDTO);
        deporte = deporteRepository.save(deporte);
        return mapearADTO(deporte);
    }

    public DeporteDTO obtenerDeportePorId(Integer idDeporte) {
        Deporte deporte = deporteRepository.findById(idDeporte)
                .orElseThrow(() -> new IllegalArgumentException("Deporte no encontrado con ID: " + idDeporte));
        return mapearADTO(deporte);
    }

    public List<DeporteDTO> listarDeportes() {
        return deporteRepository.findAll().stream()
                .map(this::mapearADTO)
                .toList();
    }

    public DeporteDTO actualizarDeporte(Integer idDeporte, DeporteDTO deporteDTO) {
        Deporte deporte = deporteRepository.findById(idDeporte)
                .orElseThrow(() -> new IllegalArgumentException("Deporte no encontrado con ID: " + idDeporte));

        deporte.setNombreDeporte(deporteDTO.getNombreDeporte());
        deporte.setDescripcion(deporteDTO.getDescripcion());
        deporte = deporteRepository.save(deporte);
        return mapearADTO(deporte);
    }

    public void eliminarDeporte(Integer idDeporte) {
        Deporte deporte = deporteRepository.findById(idDeporte)
                .orElseThrow(() -> new IllegalArgumentException("Deporte no encontrado con ID: " + idDeporte));
        deporteRepository.delete(deporte);
    }

    private Deporte mapearADominio(DeporteDTO deporteDTO ) {
        return Deporte.builder()
                .nombreDeporte(deporteDTO.getNombreDeporte())
                .descripcion(deporteDTO.getDescripcion())
                .build();
    }

    private DeporteDTO mapearADTO(Deporte deporte) {
        return new DeporteDTO(
                deporte.getIdDeporte(),
                deporte.getNombreDeporte(),
                deporte.getDescripcion()
        );
    }
}

