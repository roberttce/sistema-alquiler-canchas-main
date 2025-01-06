package com.example.backend_alquiler_canchas.service;

import com.example.backend_alquiler_canchas.dto.CanchaDTO;
import com.example.backend_alquiler_canchas.model.Cancha;
import com.example.backend_alquiler_canchas.repository.CanchaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;

@Service
public class CanchaService {

    private final CanchaRepository canchaRepository;

    @Autowired
    public CanchaService(CanchaRepository canchaRepository) {
        this.canchaRepository = canchaRepository;
    }

    public CanchaDTO crearCancha(CanchaDTO canchaDTO) {
        Cancha cancha = mapearADominio(canchaDTO);
        cancha = canchaRepository.save(cancha);
        return mapearADTO(cancha);
    }

    public CanchaDTO obtenerCanchaPorId(Integer idCancha) {
        Cancha cancha = canchaRepository.findById(idCancha)
                .orElseThrow(() -> new IllegalArgumentException("Cancha no encontrada con ID: " + idCancha));
        return mapearADTO(cancha);
    }

    public List<CanchaDTO> listarCanchas() {
        return canchaRepository.findAll().stream()
                .map(this::mapearADTO)
                .toList();
    }

    public CanchaDTO actualizarCancha(Integer idCancha, CanchaDTO canchaDTO) {
        Cancha cancha = canchaRepository.findById(idCancha)
                .orElseThrow(() -> new IllegalArgumentException("Cancha no encontrada con ID: " + idCancha));

        cancha.setNombreCancha(canchaDTO.getNombreCancha());
        cancha.setEstado(canchaDTO.getEstado());
        BigDecimal costoPorHora = new BigDecimal(canchaDTO.getCostoPorHora().toString());
        cancha.setCostoPorHora(costoPorHora);
        cancha = canchaRepository.save(cancha);
        return mapearADTO(cancha);
    }

    public void eliminarCancha(Integer idCancha) {
        Cancha cancha = canchaRepository.findById(idCancha)
                .orElseThrow(() -> new IllegalArgumentException("Cancha no encontrada con ID: " + idCancha));
        canchaRepository.delete(cancha);
    }

    private Cancha mapearADominio(CanchaDTO canchaDTO) {
        return Cancha.builder()
                .nombreCancha(canchaDTO.getNombreCancha())
                .estado(canchaDTO.getEstado())
                .costoPorHora(new BigDecimal(canchaDTO.getCostoPorHora().toString()))
                .build();
    }

    private CanchaDTO mapearADTO(Cancha cancha) {
        return new CanchaDTO(
                cancha.getIdCancha(),
                cancha.getNombreCancha(),
                cancha.getCostoPorHora(),
                cancha.getEstado()
        );
    }
}
