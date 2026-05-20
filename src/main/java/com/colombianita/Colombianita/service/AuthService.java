package com.colombianita.Colombianita.service;

import com.colombianita.Colombianita.dto.AuthRequest;
import com.colombianita.Colombianita.dto.AuthResponse;
import com.colombianita.Colombianita.entity.Usuario;
import com.colombianita.Colombianita.repository.UsuarioRepository;
import com.colombianita.Colombianita.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// PATRÓN: Strategy — encapsula la lógica de autenticación como una estrategia intercambiable.
//   El AuthController delega completamente en este servicio; si se cambia la estrategia
//   (ej: OAuth2, LDAP), solo se modifica esta clase sin tocar el controller.
// PATRÓN: Singleton — Spring gestiona esta clase como un bean singleton (@Service),
//   garantizando una única instancia compartida en toda la aplicación.
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

        // 2. Verificar password con BCrypt
        String passFront = request.getPassword() != null ? request.getPassword().trim() : "";
        if (!passwordEncoder.matches(passFront, usuario.getPasswordHash())) {
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