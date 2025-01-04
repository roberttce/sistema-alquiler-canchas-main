package com.example.backend_alquiler_canchas.service;

import com.example.backend_alquiler_canchas.dto.CanchaDeporteDTO;
import com.example.backend_alquiler_canchas.dto.DeporteDTO;
import com.example.backend_alquiler_canchas.model.CanchaDeporte;
import com.example.backend_alquiler_canchas.model.Deporte;
import com.example.backend_alquiler_canchas.model.Cancha;
import com.example.backend_alquiler_canchas.repository.CanchaDeporteRepository;
import com.example.backend_alquiler_canchas.repository.CanchaRepository;
import com.example.backend_alquiler_canchas.repository.DeporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class CanchaDeporteService {

    private final CanchaDeporteRepository canchaDeporteRepository;
    private final CanchaRepository canchaRepository;
    private final DeporteRepository deporteRepository;

    @Autowired
    public CanchaDeporteService(CanchaDeporteRepository canchaDeporteRepository, CanchaRepository canchaRepository, DeporteRepository deporteRepository) {
        this.canchaDeporteRepository = canchaDeporteRepository;
        this.canchaRepository = canchaRepository;
        this.deporteRepository = deporteRepository;
    }

    public CanchaDeporteDTO crearCanchaDeporte(CanchaDeporteDTO canchaDeporteDTO) {
        validarRelacionUnica(canchaDeporteDTO);
        CanchaDeporte canchaDeporte = mapearADominio(canchaDeporteDTO);
        canchaDeporte = canchaDeporteRepository.save(canchaDeporte);
        return mapearADTO(canchaDeporte);
    }

    public CanchaDeporteDTO obtenerCanchaDeportePorId(Integer idCancha, Integer idDeporte) {
        CanchaDeporte canchaDeporte = canchaDeporteRepository.findByCancha_IdCanchaAndDeporte_IdDeporte(idCancha, idDeporte)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la relación entre la cancha y el deporte."));
        return mapearADTO(canchaDeporte);
    }

    public List<CanchaDeporteDTO> listarCanchasDeportes() {
        return canchaDeporteRepository.findAll().stream()
                .map(this::mapearADTO)
                .toList();
    }

    public List<CanchaDeporteDTO> listarCanchasPorDeporte(Integer idDeporte) {
        return canchaDeporteRepository.findByDeporte_IdDeporte(idDeporte).stream()
                .map(this::mapearADTO)
                .toList();
    }

    public List<DeporteDTO> listarDeportesPorCancha(Integer idCancha) {
        List<CanchaDeporte> canchasDeportes = canchaDeporteRepository.findByCancha_IdCancha(idCancha);
        List<DeporteDTO> deportesPorCancha = new ArrayList<>();

        for (CanchaDeporte canchaDeporte : canchasDeportes) {
            deportesPorCancha.add(mapearDeporteADTO(canchaDeporte.getDeporte()));
        }
        return deportesPorCancha;
    }

    public void eliminarCanchaDeporte(Integer idCancha, Integer idDeporte) {
        CanchaDeporte canchaDeporte = canchaDeporteRepository.findByCancha_IdCanchaAndDeporte_IdDeporte(idCancha, idDeporte)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la relación entre la cancha y el deporte."));
        canchaDeporteRepository.delete(canchaDeporte);
    }

    private void validarRelacionUnica(CanchaDeporteDTO canchaDeporteDTO) {
        Optional<CanchaDeporte> canchaDeporteExistente = canchaDeporteRepository.findByCancha_IdCanchaAndDeporte_IdDeporte(
                canchaDeporteDTO.getIdCancha(), canchaDeporteDTO.getIdDeporte());
        if (canchaDeporteExistente.isPresent()) {
            throw new IllegalArgumentException("La relación entre esta cancha y deporte ya existe.");
        }
    }

    public List<CanchaDeporteDTO> asociarDeportesACancha(Integer idCancha, List<Integer> idDeportes) {
        Cancha cancha = canchaRepository.findById(idCancha)
                .orElseThrow(() -> new IllegalArgumentException("La cancha no existe"));

        List<CanchaDeporteDTO> canchasDeportesAsociados = new ArrayList<>();
        for (Integer idDeporte : idDeportes) {
            Deporte deporte = deporteRepository.findById(idDeporte)
                    .orElseThrow(() -> new IllegalArgumentException("El deporte no existe"));

            if (canchaDeporteRepository.existsByCanchaAndDeporte(cancha, deporte)) {
                throw new IllegalArgumentException("La relación entre la cancha y el deporte ya existe.");
            }

            CanchaDeporte canchaDeporte = new CanchaDeporte();
            canchaDeporte.setCancha(cancha);
            canchaDeporte.setDeporte(deporte);

            canchaDeporte = canchaDeporteRepository.save(canchaDeporte);
            canchasDeportesAsociados.add(mapearADTO(canchaDeporte));
        }
        return canchasDeportesAsociados;
    }

    private CanchaDeporte mapearADominio(CanchaDeporteDTO canchaDeporteDTO  ) {
        Cancha cancha = new Cancha();
        cancha.setIdCancha(canchaDeporteDTO.getIdCancha());
        Deporte deporte = new Deporte();
        deporte.setIdDeporte(canchaDeporteDTO.getIdDeporte());

        return CanchaDeporte.builder()
                .cancha(cancha)
                .deporte(deporte)
                .build();
    }

    private CanchaDeporteDTO mapearADTO(CanchaDeporte canchaDeporte) {
        return new CanchaDeporteDTO(
                canchaDeporte.getIdCanchaDeporte(),
                canchaDeporte.getCancha().getIdCancha(),
                canchaDeporte.getDeporte().getIdDeporte()
        );
    }

     private DeporteDTO mapearDeporteADTO(Deporte deporte) {
        return new DeporteDTO(deporte.getIdDeporte(), deporte.getNombreDeporte(), deporte.getDescripcion());
    }

}
