package com.example.backend_alquiler_canchas.controller;

import com.example.backend_alquiler_canchas.dto.CanchaDeporteDTO;
import com.example.backend_alquiler_canchas.dto.DeporteDTO;
import com.example.backend_alquiler_canchas.service.CanchaDeporteService;
import com.example.backend_alquiler_canchas.util.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canchas-deporte")
public class CanchaDeporteController {

    private final CanchaDeporteService canchaDeporteService;

    @Autowired
    public CanchaDeporteController(CanchaDeporteService canchaDeporteService) {
        this.canchaDeporteService = canchaDeporteService;
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<CanchaDeporteDTO>> crearCanchaDeporte(@Valid @RequestBody CanchaDeporteDTO canchaDeporteDTO) {
        CanchaDeporteDTO canchaDeporteCreado = canchaDeporteService.crearCanchaDeporte(canchaDeporteDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Relación entre cancha y deporte creada exitosamente", canchaDeporteCreado));
    }

    @GetMapping("/{idCancha}/{idDeporte}")
    public ResponseEntity<GlobalResponse<CanchaDeporteDTO>> obtenerCanchaDeporte(@PathVariable Integer idCancha, @PathVariable Integer idDeporte) {
        CanchaDeporteDTO canchaDeporte = canchaDeporteService.obtenerCanchaDeportePorId(idCancha, idDeporte);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Relación entre cancha y deporte encontrada", canchaDeporte));
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<CanchaDeporteDTO>>> listarCanchasDeportes() {
        List<CanchaDeporteDTO> canchasDeportes = canchaDeporteService.listarCanchasDeportes();
        return ResponseEntity.ok(new GlobalResponse<>(true, "Lista de relaciones entre canchas y deportes obtenida", canchasDeportes));
    }

    @GetMapping("/deporte/{idDeporte}")
    public ResponseEntity<GlobalResponse<List<CanchaDeporteDTO>>> listarCanchasPorDeporte(@PathVariable Integer idDeporte) {
        List<CanchaDeporteDTO> canchasDeportes = canchaDeporteService.listarCanchasPorDeporte(idDeporte);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Canchas para el deporte obtenidas", canchasDeportes));
    }

    @GetMapping("/cancha/{idCancha}/deportes")
    public ResponseEntity<GlobalResponse<List<DeporteDTO>>> listarDeportesPorCancha(@PathVariable Integer idCancha) {
        List<DeporteDTO> deportes = canchaDeporteService.listarDeportesPorCancha(idCancha);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Deportes para la cancha obtenidos", deportes));
    }

    @DeleteMapping("/{idCancha}/{idDeporte}")
    public ResponseEntity<GlobalResponse<Void>> eliminarCanchaDeporte(@PathVariable Integer idCancha, @PathVariable Integer idDeporte) {
        canchaDeporteService.eliminarCanchaDeporte(idCancha, idDeporte);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Relación entre cancha y deporte eliminada exitosamente", null));
    }
}
