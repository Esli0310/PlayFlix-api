package com.example.PlayFlix.auth;

import com.example.PlayFlix.jwt.JwtService;
import com.example.PlayFlix.models.entities.Role;
import com.example.PlayFlix.models.entities.Usuario;
import com.example.PlayFlix.models.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private UsuarioRepository repository;

    private final PasswordEncoder passwordEncoder;
    private  final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = repository.findByUsername(request.getUsername()).orElseThrow();
        String _token = jwtService.getToken(user);
        return AuthResponse.builder().token(_token).build();
    }

    public AuthResponse register(RegisterRequest request){
        Usuario user = Usuario.builder()
                .username(request.getUsername())
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(request.getEnable())
                .role(Role.USER)
                .build();

        repository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
