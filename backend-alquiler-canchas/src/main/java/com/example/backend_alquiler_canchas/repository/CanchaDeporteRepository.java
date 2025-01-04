 package com.example.backend_alquiler_canchas.repository;

import com.example.backend_alquiler_canchas.model.CanchaDeporte;
import com.example.backend_alquiler_canchas.model.Cancha;
import com.example.backend_alquiler_canchas.model.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CanchaDeporteRepository extends JpaRepository<CanchaDeporte, Integer> {

    boolean existsByCanchaAndDeporte(Cancha cancha, Deporte deporte);
 
    List<CanchaDeporte> findByCancha_IdCancha(Integer idCancha);
    Optional<CanchaDeporte> findByCancha_IdCanchaAndDeporte_IdDeporte(Integer idCancha, Integer idDeporte);
    List<CanchaDeporte> findByDeporte_IdDeporte(Integer idDeporte);
}
