package com.example.backend_alquiler_canchas.controller;

import com.example.backend_alquiler_canchas.dto.LoginRequestDTO;
import com.example.backend_alquiler_canchas.dto.LoginResponseDTO;
import com.example.backend_alquiler_canchas.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = authService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }
}
