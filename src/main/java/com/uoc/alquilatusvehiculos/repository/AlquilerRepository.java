package com.uoc.alquilatusvehiculos.repository;

import com.uoc.alquilatusvehiculos.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio de la entidad Alquiler
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
}
