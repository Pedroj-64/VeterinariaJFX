package co.edu.uniquindio.poo.veterinaria.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import co.edu.uniquindio.poo.veterinaria.model.*;

public class MenuVeterinarioController {
    
    private final Veterinaria veterinaria;
    private final Veterinario veterinarioActual;

    private MenuVeterinarioController(Veterinario veterinario) {
        this.veterinaria = Veterinaria.getInstance();
        this.veterinarioActual = veterinario;
    }

    private static MenuVeterinarioController instance;

    public static MenuVeterinarioController getInstance(Veterinario veterinario) {
        if (instance == null || instance.veterinarioActual != veterinario) {
            instance = new MenuVeterinarioController(veterinario);
        }
        return instance;
    }

    /**
     * Obtiene las mascotas que tienen cita con el veterinario en la fecha actual
     */
    public List<Mascota> obtenerPacientesDelDia() {
        return veterinarioActual.obtenerCitasPorFecha(LocalDate.now()).stream()
            .map(Cita::getMascota)
            .toList();
    }

    /**
     * Registra una consulta para una cita específica
     */
    public void registrarConsulta(Mascota mascota, String diagnostico, String tratamiento) {
        Optional<Cita> citaOpt = veterinarioActual.obtenerCitasPorFecha(LocalDate.now()).stream()
            .filter(cita -> cita.getMascota().equals(mascota))
            .findFirst();

        if (citaOpt.isEmpty()) {
            throw new IllegalArgumentException("No se encontró una cita para esta mascota en el día de hoy");
        }

        Cita cita = citaOpt.get();
        
        Consulta consulta = Consulta.builder()
            .cita(cita)
            .diagnostico(diagnostico)
            .tratamiento(tratamiento)
            .fecha(LocalDate.now())
            .build();

        cita.setConsulta(consulta);
    }

    /**
     * Obtiene la cita actual para una mascota específica
     */
    public Optional<Cita> obtenerCitaMascota(Mascota mascota) {
        return veterinarioActual.obtenerCitasPorFecha(LocalDate.now()).stream()
            .filter(cita -> cita.getMascota().equals(mascota))
            .findFirst();
    }
}
