 package com.example.backend_alquiler_canchas.controller;

import com.example.backend_alquiler_canchas.service.ReporteService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/semana")
    public ResponseEntity<InputStreamResource> getReporteSemanal(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws IOException {
        ByteArrayInputStream stream = reporteService.generarReporteSemanal(fecha);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reportesFhaqchas.xlsx");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(new InputStreamResource(stream));
    }
    // Endpoint para reporte diario
    @GetMapping("/diario")
    public ResponseEntity<InputStreamResource> getReporteDiario(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws IOException {
        ByteArrayInputStream stream = reporteService.generarReporteDiario(fecha);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporteDiario.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(stream));
    }
    @GetMapping("/mes")
    public ResponseEntity<InputStreamResource> getReporteMensual(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws IOException {
        ByteArrayInputStream stream = reporteService.generarReporteMensual(fecha);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporteMensual.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(stream));
    }

    // Endpoint para reporte semanal por cancha
    /*@GetMapping("/semana/cancha")
    public ResponseEntity<InputStreamResource> getReporteSemanalPorCancha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("idCancha") Long idCancha) throws IOException {
        ByteArrayInputStream stream = reporteService.generarReporteSemanalPorCancha(fecha, idCancha);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporteSemanalPorCancha.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(stream));
    }*/

}
