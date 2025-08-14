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

    /**
     * Genera el historial médico de una mascota como texto
     */
    public String generarHistorialMedico(Mascota mascota) {
        StringBuilder historial = new StringBuilder();
        historial.append("HISTORIAL MÉDICO\n");
        historial.append("================\n\n");
        historial.append("Mascota: ").append(mascota.getNombre()).append("\n");
        historial.append("Especie: ").append(mascota.getEspecie()).append("\n");
        historial.append("Raza: ").append(mascota.getRaza()).append("\n");
        historial.append("Edad: ").append(mascota.getEdad()).append(" años\n");
        historial.append("Propietario: ").append(mascota.getPropietario().getNombre()).append("\n\n");
        historial.append("CONSULTAS\n");
        historial.append("=========\n\n");

        // Obtener todas las citas con consulta de la mascota
        veterinaria.obtenerCitas().stream()
            .filter(cita -> cita.getMascota().equals(mascota) && cita.getConsulta() != null)
            .sorted((c1, c2) -> c2.getFechaHora().compareTo(c1.getFechaHora())) // Ordenar por fecha descendente
            .forEach(cita -> {
                Consulta consulta = cita.getConsulta();
                historial.append("Fecha: ").append(consulta.getFecha()).append("\n");
                historial.append("Veterinario: ").append(cita.getVeterinario().getNombre()).append("\n");
                historial.append("Motivo: ").append(cita.getMotivo()).append("\n");
                historial.append("Diagnóstico: ").append(consulta.getDiagnostico()).append("\n");
                historial.append("Tratamiento: ").append(consulta.getTratamiento()).append("\n");
                historial.append("\n---\n\n");
            });

        return historial.toString();
    }
}
