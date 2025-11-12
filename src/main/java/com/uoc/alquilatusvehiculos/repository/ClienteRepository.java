package com.uoc.alquilatusvehiculos.repository;

import com.uoc.alquilatusvehiculos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio de la entidad Cliente
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
