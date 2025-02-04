package com.example.backend_alquiler_canchas.controller;

import com.example.backend_alquiler_canchas.dto.AdministradorDTO;
import com.example.backend_alquiler_canchas.service.AdministradorService;
import com.example.backend_alquiler_canchas.util.GlobalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;

    @Autowired
    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @PostMapping("/crear")
    public ResponseEntity<GlobalResponse<AdministradorDTO>> crearAdministrador(@Validated @RequestBody AdministradorDTO administradorDTO) {
        AdministradorDTO administradorCreado = administradorService.crearAdministrador(administradorDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Administrador creado exitosamente", administradorCreado));
    }

    @GetMapping("/{idAdministrador}")
    public ResponseEntity<GlobalResponse<AdministradorDTO>> obtenerAdministradorPorId(@PathVariable Integer idAdministrador) {
        AdministradorDTO administrador = administradorService.obtenerAdministradorPorId(idAdministrador);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Administrador encontrado", administrador));
    }

    @GetMapping("/getall")
    public ResponseEntity<GlobalResponse<List<AdministradorDTO>>> listarAdministradores() {
        List<AdministradorDTO> administradores = administradorService.listarAdministradores();
        return ResponseEntity.ok(new GlobalResponse<>(true, "Lista de administradores obtenida exitosamente", administradores));
    }

    @PutMapping("/{idAdministrador}")
    public ResponseEntity<GlobalResponse<AdministradorDTO>> actualizarAdministrador(@PathVariable Integer idAdministrador, @Validated @RequestBody AdministradorDTO administradorDTO) {
        AdministradorDTO administradorActualizado = administradorService.actualizarAdministrador(idAdministrador, administradorDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Administrador actualizado exitosamente", administradorActualizado));
    }

    @DeleteMapping("/{idAdministrador}")
    public ResponseEntity<GlobalResponse<Void>> eliminarAdministrador(@PathVariable Integer idAdministrador) {
        administradorService.eliminarAdministrador(idAdministrador);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Administrador eliminado exitosamente", null));
    }
}