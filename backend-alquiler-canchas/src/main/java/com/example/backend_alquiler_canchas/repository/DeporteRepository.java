package com.example.backend_alquiler_canchas.repository;

import com.example.backend_alquiler_canchas.model.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeporteRepository extends JpaRepository<Deporte, Integer> {
}
