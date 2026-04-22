package com.colombianita.Colombianita.service;

import com.colombianita.Colombianita.dto.AuthRequest;
import com.colombianita.Colombianita.dto.AuthResponse;
import com.colombianita.Colombianita.entity.Usuario;
import com.colombianita.Colombianita.repository.UsuarioRepository;
import com.colombianita.Colombianita.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest request) {
        // 1. Buscar al usuario
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Error: No se encontró el usuario con el email: " + request.getEmail()));

        // DEBUG TEMPORAL: Imprimir en consola para ver qué estamos comparando realmente
        System.out.println("--- DEBUG LOGIN ---");
        System.out.println("Email consultado: [" + request.getEmail() + "]");
        System.out.println("Password (FRONTEND): [" + request.getPassword() + "]");
        System.out.println("Password (BASE DE DATOS): [" + usuario.getPasswordHash() + "]");
        System.out.println("-------------------");

        // 2. Verificar password en TEXTO PLANO (Temporal para desarrollo)
        // Usamos .trim() para evitar que espacios en blanco invisibles rompan la validación
        String passFront = request.getPassword() != null ? request.getPassword().trim() : "";
        String passDB = usuario.getPasswordHash() != null ? usuario.getPasswordHash().trim() : "";
        
        if (!passFront.equals(passDB)) {
            throw new RuntimeException("Error: La contraseña es incorrecta");
        }

        // 3. Generar JWT
        String token = jwtUtil.generateToken(usuario.getEmail());

        // 4. Construir la respuesta estructurada
        AuthResponse.RolInfo rolInfo = new AuthResponse.RolInfo();
        rolInfo.setIdRol(usuario.getRol().getIdRol());
        rolInfo.setNombre(usuario.getRol().getNombre());

        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
        userInfo.setIdUsuario(usuario.getIdUsuario());
        userInfo.setNombres(usuario.getNombres());
        userInfo.setEmail(usuario.getEmail());
        userInfo.setEstado(usuario.getEstado());
        userInfo.setRol(rolInfo);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUser(userInfo);

        return response;
    }
}