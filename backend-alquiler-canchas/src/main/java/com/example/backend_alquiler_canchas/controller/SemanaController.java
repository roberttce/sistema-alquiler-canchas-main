 package com.example.backend_alquiler_canchas.controller;

import com.example.backend_alquiler_canchas.dto.SemanaDTO;
import com.example.backend_alquiler_canchas.service.SemanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SemanaController {

    @Autowired
    private SemanaService semanaService;
    @GetMapping("/semana")
    public List<SemanaDTO> getSemana(@RequestParam(value = "offset", defaultValue = "0") int offset) {
        return semanaService.obtenerSemana(offset);
    }
}
