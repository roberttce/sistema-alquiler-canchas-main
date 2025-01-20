package com.example.backend_alquiler_canchas.repository;

import com.example.backend_alquiler_canchas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCorreoElectronico(String correoElectronico);
    Optional<Cliente> findByDni(String dni);
     @Query("SELECT c.reservasIncompletas FROM Cliente c WHERE c.idCliente = :idCliente")
    Optional<Integer> findReservasIncompletasByIdCliente(Integer idCliente); 
}
