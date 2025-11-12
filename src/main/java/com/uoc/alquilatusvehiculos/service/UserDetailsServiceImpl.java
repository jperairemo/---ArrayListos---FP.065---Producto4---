package com.uoc.alquilatusvehiculos.service;

import com.uoc.alquilatusvehiculos.model.User;
import com.uoc.alquilatusvehiculos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        // Permite iniciar sesión con nombre de usuario o con email
        User user = userRepository.findByUsername(input)
                .orElseGet(() -> userRepository.findByEmail(input)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + input)));

        List<String> authorities = (user.getRoles()==null || user.getRoles().isEmpty())
                ? List.of("ROLE_USER")
                : user.getRoles().stream()
                .map(r -> r.getName().startsWith("ROLE_") ? r.getName() : "ROLE_" + r.getName())
                .toList();

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())      // o user.getEmail() si prefieres mostrar email
                .password(user.getPassword())      // DEBE estar codificada (BCrypt)
                .authorities(authorities.toArray(String[]::new))
                .build();
    }
}
