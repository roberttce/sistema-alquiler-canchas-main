package com.example.backend_alquiler_canchas.controller;

import com.example.backend_alquiler_canchas.dto.CanchaDTO;
import com.example.backend_alquiler_canchas.dto.CanchaDeporteDTO;
import com.example.backend_alquiler_canchas.service.CanchaService;
import com.example.backend_alquiler_canchas.service.CanchaDeporteService;
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
    private final CanchaDeporteService canchaDeporteService;

    @Autowired
    public CanchaController(CanchaService canchaService, CanchaDeporteService canchaDeporteService) {
        this.canchaService = canchaService;
        this.canchaDeporteService = canchaDeporteService;
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

     @PostMapping("/{idCancha}/deportes")
    public ResponseEntity<GlobalResponse<List<CanchaDeporteDTO>>> asociarDeportesACancha(
        @PathVariable Integer idCancha, 
        @RequestBody List<Integer> idDeportes) {
            
        List<CanchaDeporteDTO> canchasDeportesAsociados = canchaDeporteService.asociarDeportesACancha(idCancha, idDeportes);
        
        return ResponseEntity.ok(new GlobalResponse<>(true, "Deportes asociados a la cancha exitosamente", canchasDeportesAsociados));
    }

}
