package com.example.backend_alquiler_canchas.repository;

import com.example.backend_alquiler_canchas.model.Cancha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanchaRepository extends JpaRepository<Cancha, Integer> {
}
