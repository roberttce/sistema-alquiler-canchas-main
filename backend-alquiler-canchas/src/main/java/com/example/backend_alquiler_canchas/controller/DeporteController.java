package com.example.backend_alquiler_canchas.controller;

import com.example.backend_alquiler_canchas.dto.DeporteDTO;
import com.example.backend_alquiler_canchas.service.DeporteService;
import com.example.backend_alquiler_canchas.util.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deportes")
public class DeporteController {

    private final DeporteService deporteService;

    @Autowired
    public DeporteController(DeporteService deporteService) {
        this.deporteService = deporteService;
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<DeporteDTO>> crearDeporte(@Valid @RequestBody DeporteDTO deporteDTO) {
        DeporteDTO creado = deporteService.crearDeporte(deporteDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Deporte creado exitosamente", creado));
    }

    @GetMapping("/{idDeporte}")
    public ResponseEntity<GlobalResponse<DeporteDTO>> obtenerDeportePorId(@PathVariable Integer idDeporte) {
        DeporteDTO deporte = deporteService.obtenerDeportePorId(idDeporte);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Deporte encontrado", deporte));
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<DeporteDTO>>> listarDeportes() {
        List<DeporteDTO> deportes = deporteService.listarDeportes();
        return ResponseEntity.ok(new GlobalResponse<>(true, "Lista de deportes obtenida exitosamente", deportes));
    }

    @PutMapping("/{idDeporte}")
    public ResponseEntity<GlobalResponse<DeporteDTO>> actualizarDeporte(@PathVariable Integer idDeporte, @Valid @RequestBody DeporteDTO deporteDTO) {
        DeporteDTO actualizado = deporteService.actualizarDeporte(idDeporte, deporteDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Deporte actualizado exitosamente", actualizado));
    }

    @DeleteMapping("/{idDeporte}")
    public ResponseEntity<GlobalResponse<Void>> eliminarDeporte(@PathVariable Integer idDeporte) {
        deporteService.eliminarDeporte(idDeporte);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Deporte eliminado exitosamente", null));
    }
}
