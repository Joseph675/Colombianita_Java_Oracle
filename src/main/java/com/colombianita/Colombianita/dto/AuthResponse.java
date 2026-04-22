package com.colombianita.Colombianita.dto;

public class AuthResponse {

    private String token;
    private UserInfo user;

    public AuthResponse() {}

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public UserInfo getUser() { return user; }
    public void setUser(UserInfo user) { this.user = user; }

    // Clases anidadas para estructurar el JSON exactamente como el frontend lo pide
    public static class UserInfo {
        private Long idUsuario;
        private String nombres;
        private String email;
        private Integer estado;
        private RolInfo rol;

        public Long getIdUsuario() { return idUsuario; }
        public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

        public String getNombres() { return nombres; }
        public void setNombres(String nombres) { this.nombres = nombres; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public Integer getEstado() { return estado; }
        public void setEstado(Integer estado) { this.estado = estado; }

        public RolInfo getRol() { return rol; }
        public void setRol(RolInfo rol) { this.rol = rol; }
    }

    public static class RolInfo {
        private Long idRol;
        private String nombre;

        public Long getIdRol() { return idRol; }
        public void setIdRol(Long idRol) { this.idRol = idRol; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }
}