package com.uoc.alquilatusvehiculos.controller;

import com.uoc.alquilatusvehiculos.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ClienteRepository clienteRepo;
    private final VehiculoRepository vehiculoRepo;
    private final ExtraRepository extraRepo;
    private final AlquilerRepository alquilerRepo;

    public AdminController(ClienteRepository c, VehiculoRepository v, ExtraRepository e, AlquilerRepository a) {
        this.clienteRepo = c; this.vehiculoRepo = v; this.extraRepo = e; this.alquilerRepo = a;
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/admin/alquileres"; // o /admin/vehiculos
    }

    @GetMapping("/clientes")
    public String clientes(Model model) {
        model.addAttribute("titulo","Clientes");
        model.addAttribute("items", clienteRepo.findAll());
        return "admin/clientes";
    }

    @GetMapping("/vehiculos")
    public String vehiculos(Model model) {
        model.addAttribute("titulo","Vehículos");
        model.addAttribute("items", vehiculoRepo.findAll());
        return "admin/vehiculos";
    }

    @GetMapping("/extras")
    public String extras(Model model) {
        model.addAttribute("titulo","Extras");
        model.addAttribute("items", extraRepo.findAll());
        return "admin/extras";
    }

    @GetMapping("/alquileres")
    public String alquileres(Model model) {
        model.addAttribute("titulo","Alquileres");
        model.addAttribute("items", alquilerRepo.findAll());
        return "admin/alquileres";
    }


    // ------------------------------------
// CRUD de Clientes
// ------------------------------------

    @GetMapping("/clientes/nuevo")
    public String nuevoCliente(Model model) {
        model.addAttribute("titulo", "Nuevo Cliente");
        model.addAttribute("cliente", new com.uoc.alquilatusvehiculos.model.Cliente());
        model.addAttribute("accion", "/admin/clientes/guardar");
        return "admin/cliente_form";
    }

    @PostMapping("/clientes/guardar")
    public String guardarCliente(@ModelAttribute("cliente") com.uoc.alquilatusvehiculos.model.Cliente cliente) {
        clienteRepo.save(cliente);
        return "redirect:/admin/clientes";
    }

    @GetMapping("/clientes/editar/{id}")
    public String editarCliente(@PathVariable Long id, Model model) {
        var cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
        model.addAttribute("titulo", "Editar Cliente");
        model.addAttribute("cliente", cliente);
        model.addAttribute("accion", "/admin/clientes/actualizar/" + id);
        return "admin/cliente_form";
    }

    @PostMapping("/clientes/actualizar/{id}")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute("cliente") com.uoc.alquilatusvehiculos.model.Cliente cliente) {
        cliente.setId(id);
        clienteRepo.save(cliente);
        return "redirect:/admin/clientes";
    }

    @PostMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteRepo.deleteById(id);
        return "redirect:/admin/clientes";
    }


    // ------------------------------------
// CRUD de Vehículos
// ------------------------------------

    @GetMapping("/vehiculos/nuevo")
    public String nuevoVehiculo(Model model) {
        model.addAttribute("titulo", "Nuevo Vehículo");
        model.addAttribute("vehiculo", new com.uoc.alquilatusvehiculos.model.Vehiculo());
        model.addAttribute("accion", "/admin/vehiculos/guardar");
        return "admin/vehiculo_form";
    }

    @PostMapping("/vehiculos/guardar")
    public String guardarVehiculo(@ModelAttribute("vehiculo") com.uoc.alquilatusvehiculos.model.Vehiculo vehiculo) {
        vehiculoRepo.save(vehiculo);
        return "redirect:/admin/vehiculos";
    }

    @GetMapping("/vehiculos/editar/{id}")
    public String editarVehiculo(@PathVariable Long id, Model model) {
        var vehiculo = vehiculoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado: " + id));
        model.addAttribute("titulo", "Editar Vehículo");
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("accion", "/admin/vehiculos/actualizar/" + id);
        return "admin/vehiculo_form";
    }

    @PostMapping("/vehiculos/actualizar/{id}")
    public String actualizarVehiculo(@PathVariable Long id, @ModelAttribute("vehiculo") com.uoc.alquilatusvehiculos.model.Vehiculo vehiculo) {
        vehiculo.setId(id);
        vehiculoRepo.save(vehiculo);
        return "redirect:/admin/vehiculos";
    }

    @PostMapping("/vehiculos/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable Long id) {
        vehiculoRepo.deleteById(id);
        return "redirect:/admin/vehiculos";
    }


    // ------------------------------------
// CRUD de Extras
// ------------------------------------

    @GetMapping("/extras/nuevo")
    public String nuevoExtra(Model model) {
        model.addAttribute("titulo", "Nuevo Extra");
        model.addAttribute("extra", new com.uoc.alquilatusvehiculos.model.Extra());
        model.addAttribute("accion", "/admin/extras/guardar");
        return "admin/extra_form";
    }

    @PostMapping("/extras/guardar")
    public String guardarExtra(@ModelAttribute("extra") com.uoc.alquilatusvehiculos.model.Extra extra) {
        extraRepo.save(extra);
        return "redirect:/admin/extras";
    }

    @GetMapping("/extras/editar/{id}")
    public String editarExtra(@PathVariable Long id, Model model) {
        var extra = extraRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Extra no encontrado: " + id));
        model.addAttribute("titulo", "Editar Extra");
        model.addAttribute("extra", extra);
        model.addAttribute("accion", "/admin/extras/actualizar/" + id);
        return "admin/extra_form";
    }

    @PostMapping("/extras/actualizar/{id}")
    public String actualizarExtra(@PathVariable Long id,
                                  @ModelAttribute("extra") com.uoc.alquilatusvehiculos.model.Extra extra) {
        extra.setId(id);
        extraRepo.save(extra);
        return "redirect:/admin/extras";
    }

    @PostMapping("/extras/eliminar/{id}")
    public String eliminarExtra(@PathVariable Long id) {
        extraRepo.deleteById(id);
        return "redirect:/admin/extras";
    }


    // ------------------------------------
// CRUD de Alquileres
// ------------------------------------

    @GetMapping("/alquileres/nuevo")
    public String nuevoAlquiler(Model model) {
        // Creamos un alquiler vacío
        var alquiler = new com.uoc.alquilatusvehiculos.model.Alquiler();
        alquiler.setEstado("Pendiente"); // valor por defecto

        model.addAttribute("titulo", "Nuevo Alquiler");
        model.addAttribute("alquiler", alquiler);
        model.addAttribute("accion", "/admin/alquileres/guardar");

        // Listas para los selects/checkboxes
        model.addAttribute("clientes", clienteRepo.findAll());
        model.addAttribute("vehiculos", vehiculoRepo.findAll());
        model.addAttribute("extras", extraRepo.findAll());

        return "admin/alquiler_form";
    }

    @PostMapping("/alquileres/guardar")
    public String guardarAlquiler(
            @ModelAttribute("alquiler") com.uoc.alquilatusvehiculos.model.Alquiler alquiler
    ) {
        // Calculamos el precio total antes de guardar
        alquiler.calcularPrecioTotal();

        alquilerRepo.save(alquiler);
        return "redirect:/admin/alquileres";
    }

    @GetMapping("/alquileres/editar/{id}")
    public String editarAlquiler(@PathVariable Long id, Model model) {
        var alquiler = alquilerRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado: " + id));

        model.addAttribute("titulo", "Editar Alquiler");
        model.addAttribute("alquiler", alquiler);
        model.addAttribute("accion", "/admin/alquileres/actualizar/" + id);

        // volvemos a pasar las listas para los selects del form
        model.addAttribute("clientes", clienteRepo.findAll());
        model.addAttribute("vehiculos", vehiculoRepo.findAll());
        model.addAttribute("extras", extraRepo.findAll());

        return "admin/alquiler_form";
    }

    @PostMapping("/alquileres/actualizar/{id}")
    public String actualizarAlquiler(
            @PathVariable Long id,
            @ModelAttribute("alquiler") com.uoc.alquilatusvehiculos.model.Alquiler alquiler
    ) {
        alquiler.setId(id);
        alquiler.calcularPrecioTotal();
        alquilerRepo.save(alquiler);
        return "redirect:/admin/alquileres";
    }

    @PostMapping("/alquileres/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable Long id) {
        alquilerRepo.deleteById(id);
        return "redirect:/admin/alquileres";
    }




}
