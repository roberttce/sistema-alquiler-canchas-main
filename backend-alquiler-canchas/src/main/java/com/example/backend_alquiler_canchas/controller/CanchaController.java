package com.example.backend_alquiler_canchas.controller;

import com.example.backend_alquiler_canchas.dto.CanchaDTO;
import com.example.backend_alquiler_canchas.service.CanchaService;
import com.example.backend_alquiler_canchas.util.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canchas")
public class CanchaController {

    private final CanchaService canchaService;

    @Autowired
    public CanchaController(CanchaService canchaService) {
        this.canchaService = canchaService;
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<CanchaDTO>> crearCancha(@Valid @RequestBody CanchaDTO canchaDTO) {
        CanchaDTO creado = canchaService.crearCancha(canchaDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Cancha creada exitosamente", creado));
    }

    @GetMapping("/{idCancha}")
    public ResponseEntity<GlobalResponse<CanchaDTO>> obtenerCanchaPorId(@PathVariable Integer idCancha) {
        CanchaDTO cancha = canchaService.obtenerCanchaPorId(idCancha);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Cancha encontrada", cancha));
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<CanchaDTO>>> listarCanchas() {
        List<CanchaDTO> canchas = canchaService.listarCanchas();
        return ResponseEntity.ok(new GlobalResponse<>(true, "Lista de canchas obtenida exitosamente", canchas));
    }

    @PutMapping("/{idCancha}")
    public ResponseEntity<GlobalResponse<CanchaDTO>> actualizarCancha(@PathVariable Integer idCancha, @Valid @RequestBody CanchaDTO canchaDTO) {
        CanchaDTO actualizado = canchaService.actualizarCancha(idCancha, canchaDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Cancha actualizada exitosamente", actualizado));
    }

    @DeleteMapping("/{idCancha}")
    public ResponseEntity<GlobalResponse<Void>> eliminarCancha(@PathVariable Integer idCancha) {
        canchaService.eliminarCancha(idCancha);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Cancha eliminada exitosamente", null));
    }
}
