package com.uoc.alquilatusvehiculos.api;

import com.uoc.alquilatusvehiculos.model.Alquiler;
import com.uoc.alquilatusvehiculos.model.User;
import com.uoc.alquilatusvehiculos.repository.AlquilerRepository;
import com.uoc.alquilatusvehiculos.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secure")
@RequiredArgsConstructor
@Tag(name = "Endpoints securizados con token")
public class SecureApiController {

    private final UserRepository userRepository;
    private final AlquilerRepository alquilerRepository;

    // 1) GET seguro: lista todos los usuarios
    @GetMapping("/users")
    @Operation(summary = "Listado de usuarios (requiere token X-API-TOKEN)")
    public List<User> getAllUsersSecure() {
        return userRepository.findAll();
    }

    // 2) DELETE seguro: borrar usuario por id
    @DeleteMapping("/users/{id}")
    @Operation(summary = "Borrar usuario por id (requiere token X-API-TOKEN)")
    public ResponseEntity<String> deleteUserSecure(@PathVariable Long id) {

        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok("Usuario eliminado correctamente");
                })
                .orElseGet(() ->
                        ResponseEntity.status(404)
                                .body("Usuario no encontrado")
                );
    }


    // 3) DELETE seguro: borrar alquiler por id
    @DeleteMapping("/alquileres/{id}")
    @Operation(summary = "Borrar alquiler por id (requiere token X-API-TOKEN)")
    public ResponseEntity<String> deleteAlquilerSecure(@PathVariable Long id) {

        return alquilerRepository.findById(id)
                .map(alquiler -> {
                    alquilerRepository.delete(alquiler);
                    return ResponseEntity.ok("Alquiler eliminado correctamente");
                })
                .orElseGet(() ->
                        ResponseEntity.status(404)
                                .body("Alquiler no encontrado")
                );
    }

}
