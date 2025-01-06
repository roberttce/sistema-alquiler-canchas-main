package com.example.backend_alquiler_canchas.service;

import com.example.backend_alquiler_canchas.dto.AdministradorDTO;
import com.example.backend_alquiler_canchas.model.Administrador;
import com.example.backend_alquiler_canchas.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdministradorService(AdministradorRepository administradorRepository, BCryptPasswordEncoder passwordEncoder) {
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdministradorDTO crearAdministrador(AdministradorDTO administradorDTO) {
        validarAdministradorUnico(administradorDTO);
        administradorDTO.setContrasena(passwordEncoder.encode(administradorDTO.getContrasena()));
        Administrador administrador = mapearADominio(administradorDTO);
        administrador = administradorRepository.save(administrador);
        return mapearADTO(administrador);
    }

    public AdministradorDTO obtenerAdministradorPorId(Integer idAdministrador) {
        Administrador administrador = administradorRepository.findById(idAdministrador)
                .orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado con ID: " + idAdministrador));
        return mapearADTO(administrador);
    }

    public List<AdministradorDTO> listarAdministradores() {
        return administradorRepository.findAll()
                .stream()
                .map(this::mapearADTO)
                .toList();
    }

    public AdministradorDTO actualizarAdministrador(Integer idAdministrador, AdministradorDTO administradorDTO) {
        Administrador administradorExistente = administradorRepository.findById(idAdministrador)
                .orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado con ID: " + idAdministrador));

        administradorExistente.setNombre(administradorDTO.getNombre());
        administradorExistente.setApellido(administradorDTO.getApellido());
        administradorExistente.setUsuario(administradorDTO.getUsuario());
        administradorExistente.setCorreoElectronico(administradorDTO.getCorreoElectronico());

        if (administradorDTO.getContrasena() != null && !administradorDTO.getContrasena().isEmpty()) {
            administradorExistente.setContrasena(passwordEncoder.encode(administradorDTO.getContrasena()));
        }

        administradorExistente = administradorRepository.save(administradorExistente);
        return mapearADTO(administradorExistente);
    }

    public void eliminarAdministrador(Integer idAdministrador) {
        if (!administradorRepository.existsById(idAdministrador)) {
            throw new IllegalArgumentException("Administrador no encontrado con ID: " + idAdministrador);
        }
        administradorRepository.deleteById(idAdministrador);
    }

    private void validarAdministradorUnico(AdministradorDTO administradorDTO) {
        administradorRepository.findByUsuario(administradorDTO.getUsuario())
                .ifPresent(administrador -> {
                    throw new IllegalArgumentException("Ya existe un administrador con el usuario: " + administradorDTO.getUsuario());
                });

        administradorRepository.findByCorreoElectronico(administradorDTO.getCorreoElectronico())
                .ifPresent(administrador -> {
                    throw new IllegalArgumentException("Ya existe un administrador con el correo: " + administradorDTO.getCorreoElectronico());
                });
    }

    private Administrador mapearADominio(AdministradorDTO administradorDTO) {
        return Administrador.builder()
                .nombre(administradorDTO.getNombre())
                .apellido(administradorDTO.getApellido())
                .usuario(administradorDTO.getUsuario())
                .correoElectronico(administradorDTO.getCorreoElectronico())
                .contrasena(administradorDTO.getContrasena())
                .build();
    }

    private AdministradorDTO mapearADTO(Administrador administrador) {
        return new AdministradorDTO(
                administrador.getIdAdministrador(),
                administrador.getNombre(),
                administrador.getApellido(),
                administrador.getUsuario(),
                administrador.getCorreoElectronico(),
                null
        );
    }
}
