package com.example.backend_alquiler_canchas.service;

import com.example.backend_alquiler_canchas.dto.LoginRequestDTO;
import com.example.backend_alquiler_canchas.dto.LoginResponseDTO;
import com.example.backend_alquiler_canchas.model.Administrador;
import com.example.backend_alquiler_canchas.repository.AdministradorRepository;
import com.example.backend_alquiler_canchas.config.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdministradorRepository administradorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Administrador admin = administradorRepository
                .findByUsuarioOrCorreoElectronico(loginRequestDTO.getUsuario(), loginRequestDTO.getUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario o correo no encontrado."));

        validarContrasena(loginRequestDTO.getContrasena(), admin.getContrasena());

        String token = jwtTokenUtil.generateToken(admin.getUsuario());

        return mapearADTO(admin, token);
    }

    private LoginResponseDTO mapearADTO(Administrador administrador, String token) {
        return new LoginResponseDTO(
                administrador.getIdAdministrador(),
                administrador.getNombre(),
                administrador.getApellido(),
                administrador.getUsuario(),
                administrador.getCorreoElectronico(),
                token
        );
    }

    private Administrador mapearADominio(LoginRequestDTO loginRequestDTO) {
        Administrador administrador = new Administrador();
        administrador.setUsuario(loginRequestDTO.getUsuario());
        administrador.setContrasena(loginRequestDTO.getContrasena());
        return administrador;
    }

    private void validarContrasena(String contrasenaIngresada, String contrasenaAlmacenada) {
        if (!passwordEncoder.matches(contrasenaIngresada, contrasenaAlmacenada)) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }
    }
}
