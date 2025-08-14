package co.edu.uniquindio.poo.veterinaria.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veterinario {
    private String cedula;
    private String nombre;
    private String especialidad;
    private String telefono;
    private String email;
    @Builder.Default
    private List<Cita> citas = new ArrayList<>();
    @Builder.Default
    private Map<LocalDate, Set<LocalTime>> horariosDisponibles = new HashMap<>();

    public void agregarCita(Cita cita) {
        citas.add(cita);
        // Al agregar una cita, removemos ese horario de los disponibles
        LocalDateTime fechaHora = cita.getFechaHora();
        ocuparHorario(fechaHora.toLocalDate(), fechaHora.toLocalTime());
    }

    public List<Cita> obtenerCitasPorFecha(LocalDate fecha) {
        return citas.stream()
            .filter(cita -> cita.getFechaHora().toLocalDate().equals(fecha))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene los horarios disponibles para una fecha específica
     * @param fecha La fecha para la cual se quieren obtener los horarios
     * @return Lista de horarios disponibles ordenados
     */
    public List<LocalTime> getHorariosDisponibles(LocalDate fecha) {
        if (!horariosDisponibles.containsKey(fecha)) {
            inicializarHorariosDelDia(fecha);
        }
        return new ArrayList<>(horariosDisponibles.get(fecha)).stream()
            .sorted()
            .collect(Collectors.toList());
    }

    private void inicializarHorariosDelDia(LocalDate fecha) {
        Set<LocalTime> horariosDia = new HashSet<>();
        // Horarios de 8 AM a 4 PM
        for (int hora = 8; hora < 16; hora++) {
            horariosDia.add(LocalTime.of(hora, 0));
        }
        // Remover horarios que ya están ocupados
        obtenerCitasPorFecha(fecha).forEach(cita -> 
            horariosDia.remove(cita.getFechaHora().toLocalTime())
        );
        horariosDisponibles.put(fecha, horariosDia);
    }

    /**
     * Verifica si un horario específico está disponible
     */
    public boolean estaDisponible(LocalDate fecha, LocalTime hora) {
        return getHorariosDisponibles(fecha).contains(hora);
    }

    /**
     * Marca un horario como ocupado
     * @return true si se pudo ocupar el horario, false si ya estaba ocupado
     */
    public boolean ocuparHorario(LocalDate fecha, LocalTime hora) {
        Set<LocalTime> horarios = horariosDisponibles.computeIfAbsent(fecha, k -> new HashSet<>());
        return horarios.remove(hora);
    }

    public List<Consulta> obtenerConsultasRealizadas() {
        return citas.stream()
            .map(Cita::getConsulta)
            .filter(consulta -> consulta != null)
            .collect(Collectors.toList());
    }
}
