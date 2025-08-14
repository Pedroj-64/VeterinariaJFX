package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;

public class LoginController {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    private LoginController() {}

    public static boolean validarCredenciales(String usuario, String contraseña) {
        if (usuario == null || contraseña == null || usuario.isEmpty() || contraseña.isEmpty()) {
            return false;
        }

        // Verificar si es administrador
        if (ADMIN_USERNAME.equals(usuario) && ADMIN_PASSWORD.equals(contraseña)) {
            return true;
        }

        // Verificar si es veterinario
        var veterinaria = Veterinaria.getInstance();
        var veterinarioOpt = veterinaria.buscarVeterinario(contraseña); // Usando la cédula como contraseña
        return veterinarioOpt.isPresent() && veterinarioOpt.get().getNombre().equalsIgnoreCase(usuario);
    }

    public static boolean esAdmin(String usuario, String contraseña) {
        return ADMIN_USERNAME.equals(usuario) && ADMIN_PASSWORD.equals(contraseña);
    }
}