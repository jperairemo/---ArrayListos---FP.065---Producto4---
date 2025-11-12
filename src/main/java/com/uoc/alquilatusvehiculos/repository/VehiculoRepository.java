package com.uoc.alquilatusvehiculos.repository;

import com.uoc.alquilatusvehiculos.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio de la entidad Vehiculo
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
}
