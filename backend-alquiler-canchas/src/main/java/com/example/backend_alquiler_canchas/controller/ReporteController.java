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
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    private String getNombreMes(LocalDate fecha) {
        return fecha.getMonth().getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "ES"));
    }

    private String getNombreDia(LocalDate fecha) {
        return fecha.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "ES"));
    }

    private String formatoFecha(LocalDate fecha) {
        return fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @GetMapping("/semana")
    public ResponseEntity<InputStreamResource> getReporteSemanal(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws IOException {
        ByteArrayInputStream stream = reporteService.generarReporteSemanal(fecha);
        String nombreArchivo = "reporte-semanal-" + getNombreMes(fecha) + "-" + formatoFecha(fecha) + ".xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + nombreArchivo);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(new InputStreamResource(stream));
    }

    @GetMapping("/diario")
    public ResponseEntity<InputStreamResource> getReporteDiario(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws IOException {
        ByteArrayInputStream stream = reporteService.generarReporteDiario(fecha);
        String nombreArchivo = "reporte-diario-" + getNombreDia(fecha) + "-" + formatoFecha(fecha) + ".xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + nombreArchivo);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(new InputStreamResource(stream));
    }

    @GetMapping("/mes")
    public ResponseEntity<InputStreamResource> getReporteMensual(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws IOException {
        ByteArrayInputStream stream = reporteService.generarReporteMensual(fecha);
        String nombreArchivo = "reporte-mensual-" + getNombreMes(fecha) + "-" + formatoFecha(fecha) + ".xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + nombreArchivo);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(new InputStreamResource(stream));
    }
}
