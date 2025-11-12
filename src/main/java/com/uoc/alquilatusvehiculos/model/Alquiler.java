package com.uoc.alquilatusvehiculos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Alquiler {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne private Cliente cliente;
    @ManyToOne private Vehiculo vehiculo;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double precioTotal;
    private String estado; // Activo / Completado / Pendiente

    @ManyToMany
    @JoinTable(
            name = "alquiler_extra",
            joinColumns = @JoinColumn(name = "alquiler_id"),
            inverseJoinColumns = @JoinColumn(name = "extra_id"))
    private Set<Extra> extras = new HashSet<>();

    // ======= Lógica simple
    public void calcularPrecioTotal() {
        if (vehiculo == null || fechaInicio == null || fechaFin == null) return;
        long dias = Math.max(1, java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin));
        double base = dias * (vehiculo.getPrecioDia() == null ? 0 : vehiculo.getPrecioDia());
        double add = extras.stream().mapToDouble(e -> e.getPrecio() == null ? 0 : e.getPrecio()).sum() * dias;
        this.precioTotal = base + add;
    }
}
