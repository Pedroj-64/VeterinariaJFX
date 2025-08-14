package co.edu.uniquindio.poo.veterinaria.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import co.edu.uniquindio.poo.veterinaria.model.*;

public class SuperUsuarioController {
    
    private final Veterinaria veterinaria;

    private SuperUsuarioController() {
        this.veterinaria = Veterinaria.getInstance();
    }

    private static class SingletonHolder {
        private static final SuperUsuarioController INSTANCE = new SuperUsuarioController();
    }

    public static SuperUsuarioController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void agendarCita(Mascota mascota, Veterinario veterinario, LocalDate fecha, LocalTime hora, String motivo) {
        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
        Cita cita = Cita.builder()
            .id(generarIdCita())
            .mascota(mascota)
            .veterinario(veterinario)
            .fechaHora(fechaHora)
            .motivo(motivo)
            .build();
        
        veterinaria.agendarCita(cita);
    }

    private String generarIdCita() {
        return "CITA-" + System.currentTimeMillis();
    }

    /**
     * Valida y registra un nuevo propietario en el sistema.
     * @param cedula Cédula del propietario
     * @param nombre Nombre completo del propietario
     * @param telefono Teléfono del propietario
     * @param email Email del propietario
     * @param direccion Dirección del propietario
     * @throws IllegalArgumentException si algún dato es inválido o si la cédula ya existe
     */
    public void registrarPropietario(String cedula, String nombre, String telefono, String email, String direccion) {
        // Validaciones
        validarDatosPropietario(cedula, nombre, telefono, email);
        validarCedulaUnica(cedula);
        
        // Crear y registrar el propietario
        Propietario propietario = Propietario.builder()
            .cedula(cedula)
            .nombre(nombre)
            .telefono(telefono)
            .email(email)
            .direccion(direccion)
            .build();
        
        veterinaria.registrarPropietario(propietario);
    }

    private void validarDatosPropietario(String cedula, String nombre, String telefono, String email) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula es requerida");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es requerido");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }
    }

    private void validarCedulaUnica(String cedula) {
        boolean existe = veterinaria.getPropietarios().stream()
            .anyMatch(p -> p.getCedula().equals(cedula));
        if (existe) {
            throw new IllegalArgumentException("Ya existe un propietario con esta cédula");
        }
    }

    /**
     * Valida y registra una nueva mascota en el sistema.
     * @param nombre Nombre de la mascota
     * @param especie Especie de la mascota
     * @param raza Raza de la mascota
     * @param edad Edad de la mascota
     * @param propietario Propietario de la mascota
     * @throws IllegalArgumentException si algún dato es inválido
     */
    public void registrarMascota(String nombre, String especie, String raza, String edad, Propietario propietario) {
        // Validaciones
        validarDatosMascota(nombre, especie, raza, edad, propietario);
        
        int edadNumerica = Integer.parseInt(edad);
        
        // Crear y registrar la mascota
        Mascota mascota = Mascota.builder()
            .id(generarIdMascota())
            .nombre(nombre)
            .especie(especie)
            .raza(raza)
            .edad(edadNumerica)
            .propietario(propietario)
            .build();
        
        veterinaria.registrarMascota(mascota);
        propietario.agregarMascota(mascota);
    }

    private void validarDatosMascota(String nombre, String especie, String raza, String edad, Propietario propietario) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota es requerido");
        }
        if (especie == null || especie.trim().isEmpty()) {
            throw new IllegalArgumentException("La especie de la mascota es requerida");
        }
        if (raza == null || raza.trim().isEmpty()) {
            throw new IllegalArgumentException("La raza de la mascota es requerida");
        }
        if (edad == null || edad.trim().isEmpty()) {
            throw new IllegalArgumentException("La edad de la mascota es requerida");
        }
        try {
            int edadNumerica = Integer.parseInt(edad);
            if (edadNumerica < 0) {
                throw new IllegalArgumentException("La edad debe ser un número positivo");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser un número válido");
        }
        if (propietario == null) {
            throw new IllegalArgumentException("Debe seleccionar un propietario");
        }
    }

    private String generarIdMascota() {
        return "MASC-" + System.currentTimeMillis();
    }

    /**
     * Valida y registra un nuevo veterinario en el sistema.
     * @param cedula Cédula del veterinario
     * @param nombre Nombre completo del veterinario
     * @param especialidad Especialidad del veterinario
     * @param telefono Teléfono del veterinario
     * @param email Email del veterinario
     * @throws IllegalArgumentException si algún dato es inválido o si la cédula ya existe
     */
    public void registrarVeterinario(String cedula, String nombre, Especialidad especialidad, String telefono, String email) {
        // Validaciones
        validarDatosVeterinario(cedula, nombre, especialidad, telefono, email);
        validarCedulaUnicaVeterinario(cedula);
        
        // Crear y registrar el veterinario
        Veterinario veterinario = Veterinario.builder()
            .cedula(cedula)
            .nombre(nombre)
            .especialidad(especialidad.getDescripcion())
            .telefono(telefono)
            .email(email)
            .build();
        
        veterinaria.registrarVeterinario(veterinario);
    }

    /**
     * Obtiene la lista de especialidades disponibles.
     * @return Lista de especialidades
     */
    public List<Especialidad> obtenerEspecialidades() {
        return Arrays.asList(Especialidad.values());
    }

    private void validarDatosVeterinario(String cedula, String nombre, Especialidad especialidad, 
            String telefono, String email) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula es requerida");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (especialidad == null) {
            throw new IllegalArgumentException("La especialidad es requerida");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es requerido");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }
    }

    private void validarCedulaUnicaVeterinario(String cedula) {
        boolean existe = veterinaria.getVeterinarios().stream()
            .anyMatch(v -> v.getCedula().equals(cedula));
        if (existe) {
            throw new IllegalArgumentException("Ya existe un veterinario con esta cédula");
        }
    }

    public java.util.List<Propietario> obtenerPropietarios() {
        return veterinaria.getPropietarios();
    }

    public java.util.List<Mascota> obtenerMascotas() {
        return veterinaria.getMascotas();
    }

    public java.util.List<Mascota> obtenerMascotasPorPropietario(Propietario propietario) {
        if (propietario == null) {
            throw new IllegalArgumentException("El propietario no puede ser nulo");
        }
        return propietario.getMascotas();
    }

    public java.util.List<Veterinario> obtenerVeterinarios() {
        return veterinaria.getVeterinarios();
    }

    public java.util.List<Cita> obtenerCitas() {
        return veterinaria.getCitas();
    }
}
