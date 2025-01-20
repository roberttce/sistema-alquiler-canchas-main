package com.example.backend_alquiler_canchas.controller;

import com.example.backend_alquiler_canchas.dto.ClienteDTO;
import com.example.backend_alquiler_canchas.service.ClienteService;
import com.example.backend_alquiler_canchas.util.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<ClienteDTO>> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteCreado = clienteService.crearCliente(clienteDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Cliente creado exitosamente", clienteCreado));
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<GlobalResponse<ClienteDTO>> obtenerClientePorId(@PathVariable Integer idCliente) {
        ClienteDTO cliente = clienteService.obtenerClientePorId(idCliente);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Cliente encontrado", cliente));
    }
    @GetMapping("/dni/{dni}")
    public ResponseEntity<GlobalResponse<ClienteDTO>> obtenerClientePorId(@PathVariable String dni) {
        ClienteDTO cliente = clienteService.obtenerClientePorDni(dni);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Cliente encontrado", cliente));
    }
    @GetMapping("/{idCliente}/reservas-incompletas")
    public ResponseEntity<GlobalResponse<Integer>> obtenerReservasIncompletas(@PathVariable Integer idCliente) {
        Integer reservasIncompletas = clienteService.obtenerReservasIncompletas(idCliente);
        return ResponseEntity.ok(new GlobalResponse<>(
            true,
            "Reservas incompletas obtenidas exitosamente.",
            reservasIncompletas
        ));
    }
    @GetMapping
    public ResponseEntity<GlobalResponse<List<ClienteDTO>>> listarClientes() {
        List<ClienteDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(new GlobalResponse<>(true, "Lista de clientes obtenida exitosamente", clientes));
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<GlobalResponse<ClienteDTO>> actualizarCliente(@PathVariable Integer idCliente, @Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteActualizado = clienteService.actualizarCliente(idCliente, clienteDTO);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Cliente actualizado exitosamente", clienteActualizado));
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<GlobalResponse<Void>> eliminarCliente(@PathVariable Integer idCliente) {
        clienteService.eliminarCliente(idCliente);
        return ResponseEntity.ok(new GlobalResponse<>(true, "Cliente eliminado exitosamente", null));
    }
}
