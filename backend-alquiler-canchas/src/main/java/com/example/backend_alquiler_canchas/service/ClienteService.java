 package com.example.backend_alquiler_canchas.service;

import com.example.backend_alquiler_canchas.dto.ClienteDTO;
import com.example.backend_alquiler_canchas.model.Cliente;
import com.example.backend_alquiler_canchas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        validarClienteUnico(clienteDTO);
        Cliente cliente = mapearADominio(clienteDTO);
        cliente.setReservasIncompletas(0); // Inicializa en 0 al crear
        cliente = clienteRepository.save(cliente);
        return mapearADTO(cliente);
    }

    public ClienteDTO obtenerClientePorId(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + idCliente));
        return mapearADTO(cliente);
    }

    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(this::mapearADTO)
                .toList();
    }

    public ClienteDTO actualizarCliente(Integer idCliente, ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + idCliente));

        clienteExistente.setNombre(clienteDTO.getNombre());
        clienteExistente.setApellido(clienteDTO.getApellido());
        clienteExistente.setCorreoElectronico(clienteDTO.getCorreoElectronico());
        clienteExistente.setTelefono(clienteDTO.getTelefono());
        clienteExistente.setDni(clienteDTO.getDni());
        clienteExistente.setDireccion(clienteDTO.getDireccion());
        clienteExistente.setFechaNacimiento(clienteDTO.getFechaNacimiento());

        clienteExistente = clienteRepository.save(clienteExistente);
        return mapearADTO(clienteExistente);
    }

    public void eliminarCliente(Integer idCliente) {
        if (!clienteRepository.existsById(idCliente)) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + idCliente);
        }
        clienteRepository.deleteById(idCliente);
    }

    private void validarClienteUnico(ClienteDTO clienteDTO) {
        clienteRepository.findByCorreoElectronico(clienteDTO.getCorreoElectronico())
                .ifPresent(cliente -> {
                    throw new IllegalArgumentException("Ya existe un cliente con el correo: " + clienteDTO.getCorreoElectronico());
                });

        clienteRepository.findByDni(clienteDTO.getDni())
                .ifPresent(cliente -> {
                    throw new IllegalArgumentException("Ya existe un cliente con el DNI: " + clienteDTO.getDni());
                });
    }

    private Cliente mapearADominio(ClienteDTO clienteDTO) {
        return Cliente.builder()
                .idCliente(clienteDTO.getIdCliente())
                .nombre(clienteDTO.getNombre())
                .apellido(clienteDTO.getApellido())
                .correoElectronico(clienteDTO.getCorreoElectronico())
                .telefono(clienteDTO.getTelefono())
                .dni(clienteDTO.getDni())
                .direccion(clienteDTO.getDireccion())
                .fechaNacimiento(clienteDTO.getFechaNacimiento())
                .reservasIncompletas(0)  
                .build();
    }

    private ClienteDTO mapearADTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getIdCliente(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getCorreoElectronico(),
                cliente.getTelefono(),
                cliente.getDni(),
                cliente.getDireccion(),
                cliente.getFechaNacimiento(),
                cliente.getReservasIncompletas()
        );
    }
}
