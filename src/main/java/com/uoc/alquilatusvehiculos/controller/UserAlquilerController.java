package com.uoc.alquilatusvehiculos.controller;

import com.uoc.alquilatusvehiculos.model.Alquiler;
import com.uoc.alquilatusvehiculos.model.Cliente;
import com.uoc.alquilatusvehiculos.repository.AlquilerRepository;
import com.uoc.alquilatusvehiculos.repository.ClienteRepository;
import com.uoc.alquilatusvehiculos.repository.ExtraRepository;
import com.uoc.alquilatusvehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAlquilerController {

    private final ClienteRepository clienteRepo;
    private final AlquilerRepository alquilerRepo;
    private final VehiculoRepository vehiculoRepo;
    private final ExtraRepository extraRepo;

    /** Marcar panel de usuario para el layout */
    private void setUser(Model model) {
        model.addAttribute("esAdmin", false);
        model.addAttribute("esUser", true);
    }

    /** Obtiene el cliente vinculado al usuario logueado */
    private Cliente getClienteLogueado(Authentication auth) {
        return clienteRepo.findByUserUsername(auth.getName())
                .orElseThrow(() ->
                        new IllegalStateException("No existe cliente asociado al usuario logueado.")
                );
    }

    /** LISTADO DE MIS ALQUILERES */
    @GetMapping("/alquileres")
    public String misAlquileres(Model model, Authentication auth) {

        setUser(model);
        var cliente = getClienteLogueado(auth);

        model.addAttribute("titulo", "Bienvenido al panel de usuario");
        model.addAttribute("subtitulo", "Mis alquileres");
        model.addAttribute("activePage", "alquileres");

        model.addAttribute("items",
                alquilerRepo.findByClienteId(cliente.getId()));

        return "user/alquileres";
    }

    /** FORMULARIO NUEVO ALQUILER */
    @GetMapping("/alquileres/nuevo")
    public String nuevoAlquiler(Model model, Authentication auth) {

        setUser(model);

        var alquiler = new Alquiler();
        alquiler.setEstado("Pendiente");

        model.addAttribute("titulo", "Bienvenido al panel de usuario");
        model.addAttribute("subtitulo", "Nuevo alquiler");
        model.addAttribute("activePage", "nuevo");

        model.addAttribute("alquiler", alquiler);

        // valores para selects
        model.addAttribute("vehiculos", vehiculoRepo.findAll());
        model.addAttribute("extras", extraRepo.findAll());

        return "user/alquiler_form";
    }

    /** GUARDAR ALQUILER DEL USUARIO */
    @PostMapping("/alquileres/guardar")
    public String guardarAlquiler(
            @ModelAttribute("alquiler") Alquiler alquiler,
            Authentication auth
    ) {
        var cliente = getClienteLogueado(auth);
        alquiler.setCliente(cliente);
        alquiler.calcularPrecioTotal();
        alquilerRepo.save(alquiler);

        return "redirect:/user/alquileres";
    }
}
