package co.edu.uniquindio.poo.veterinaria.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.*;

@Getter
@Setter

public class Veterinaria {
    private static Veterinaria instancia;
    private String nombre;
    private String direccion;

    private Veterinaria() {
        this.propietarios = new ArrayList<>();
        this.mascotas = new ArrayList<>();
        this.veterinarios = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

/** Devuelve la instancia única de la veterinaria (patrón Singleton). */
    public static Veterinaria getInstance() {
        if (instancia == null) {
            instancia = new Veterinaria();
        }
        return instancia;
    }
    private List<Propietario> propietarios;
    private List<Mascota> mascotas;
    private List<Veterinario> veterinarios;
    private List<Cita> citas;

    /** Inicializa la veterinaria con nombre y dirección, limpiando datos previos. */
    public void inicializar(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        limpiarDatos();
    }

    /** Elimina todos los datos registrados en la veterinaria. */
    public void limpiarDatos() {
        this.propietarios.clear();
        this.mascotas.clear();
        this.veterinarios.clear();
        this.citas.clear();
    }

    // CRUD Propietario
     /** Registra un nuevo propietario. */
    public void registrarPropietario(Propietario propietario) {
        validarPropietarioExistente(propietario.getCedula());
        propietarios.add(propietario);
    }

    /** Busca un propietario por su cédula. */
    public Optional<Propietario> buscarPropietario(String cedula) {
        return propietarios.stream()
                .filter(p -> p.getCedula().equals(cedula))
                .findFirst();
    }

    /** Actualiza la información de un propietario existente. */
    public void actualizarPropietario(Propietario propietario) {
        int index = encontrarIndicePropietario(propietario.getCedula());
        if (index != -1) {
            propietarios.set(index, propietario);
        }
    }

    /** Elimina un propietario por su cédula. */
    public void eliminarPropietario(String cedula) {
        propietarios.removeIf(p -> p.getCedula().equals(cedula));
    }

    // CRUD Mascota
    /** Registra una nueva mascota. */
    public void registrarMascota(Mascota mascota) {
        validarMascotaExistente(mascota.getId());
        mascotas.add(mascota);
    }

    /** Busca una mascota por su ID. */
    public Optional<Mascota> buscarMascota(String id) {
        return mascotas.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    /** Actualiza la información de una mascota existente. */
    public void actualizarMascota(Mascota mascota) {
        int index = encontrarIndiceMascota(mascota.getId());
        if (index != -1) {
            mascotas.set(index, mascota);
        }
    }
     /** Elimina una mascota por su ID. */
    public void eliminarMascota(String id) {
        mascotas.removeIf(m -> m.getId().equals(id));
    }

    // CRUD Veterinario
     /** Registra un nuevo veterinario. */
    public void registrarVeterinario(Veterinario veterinario) {
        validarVeterinarioExistente(veterinario.getCedula());
        veterinarios.add(veterinario);
    }

    /** Busca un veterinario por su cédula. */
    public Optional<Veterinario> buscarVeterinario(String cedula) {
        return veterinarios.stream()
                .filter(v -> v.getCedula().equals(cedula))
                .findFirst();
    }

    /** Actualiza la información de un veterinario existente. */
    public void actualizarVeterinario(Veterinario veterinario) {
        int index = encontrarIndiceVeterinario(veterinario.getCedula());
        if (index != -1) {
            veterinarios.set(index, veterinario);
        }
    }

    /** Elimina un veterinario por su cédula. */
    public void eliminarVeterinario(String cedula) {
        veterinarios.removeIf(v -> v.getCedula().equals(cedula));
    }

    // CRUD Citas
    /** Agenda una nueva cita validando la disponibilidad del veterinario. */
    public void agendarCita(Cita cita) {
        validarDisponibilidadHorario(cita);
        citas.add(cita);
        cita.getMascota().agregarCita(cita);
        cita.getVeterinario().agregarCita(cita); // Agrega la cita al veterinario y actualiza sus horarios
    }

    /** Busca una cita por su ID. */
    public Optional<Cita> buscarCita(String id) {
        return citas.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    /** Actualiza la información de una cita existente. */
    public void actualizarCita(Cita cita) {
        int index = encontrarIndiceCita(cita.getId());
        if (index != -1) {
            citas.set(index, cita);
        }
    }

    /** Cancela una cita por su ID. */
    public void cancelarCita(String id) {
        citas.removeIf(c -> c.getId().equals(id));
    }

    // Métodos de consulta
     /** Devuelve todas las citas registradas. */
    public List<Cita> obtenerCitas() {
        return new ArrayList<>(citas);
    }

    /** Devuelve las citas programadas en una fecha específica. */
    public List<Cita> obtenerCitasPorFecha(LocalDate fecha) {
        return citas.stream()
                .filter(c -> c.getFechaHora().toLocalDate().equals(fecha))
                .collect(Collectors.toList());
    }

    /** Devuelve las citas de un veterinario en una fecha determinada. */
    public List<Cita> obtenerCitasVeterinario(String cedulaVeterinario, LocalDate fecha) {
        return citas.stream()
                .filter(c -> c.getVeterinario().getCedula().equals(cedulaVeterinario) 
                        && c.getFechaHora().toLocalDate().equals(fecha))
                .collect(Collectors.toList());
    }

    /** Devuelve los horarios disponibles de un veterinario en una fecha dada. */
    public List<LocalTime> obtenerHorariosDisponibles(String cedulaVeterinario, LocalDate fecha) {
        Optional<Veterinario> vetOpt = buscarVeterinario(cedulaVeterinario);
        if (vetOpt.isPresent()) {
            return vetOpt.get().getHorariosDisponibles(fecha);
        }
        return new ArrayList<>();
    }

    // Métodos de validación
    /** Valida que no exista un propietario con la misma cédula. */
    private void validarPropietarioExistente(String cedula) {
        if (buscarPropietario(cedula).isPresent()) {
            throw new IllegalArgumentException("Ya existe un propietario con la cédula: " + cedula);
        }
    }

    /** Valida que no exista una mascota con el mismo ID. */
    private void validarMascotaExistente(String id) {
        if (buscarMascota(id).isPresent()) {
            throw new IllegalArgumentException("Ya existe una mascota con el ID: " + id);
        }
    }

    /** Valida que no exista un veterinario con la misma cédula. */
    private void validarVeterinarioExistente(String cedula) {
        if (buscarVeterinario(cedula).isPresent()) {
            throw new IllegalArgumentException("Ya existe un veterinario con la cédula: " + cedula);
        }
    }

    /** Valida que el veterinario tenga disponible la fecha y hora de la cita. */
    private void validarDisponibilidadHorario(Cita cita) {
        Veterinario veterinario = cita.getVeterinario();
        LocalDateTime fechaHora = cita.getFechaHora();
        if (!veterinario.estaDisponible(fechaHora.toLocalDate(), fechaHora.toLocalTime())) {
            throw new IllegalArgumentException("El horario seleccionado no está disponible");
        }
    }

    /** Verifica si un horario está disponible para un veterinario. */
    private boolean esHorarioDisponible(String cedulaVeterinario, LocalDate fecha, LocalTime hora) {
        Optional<Veterinario> vetOpt = buscarVeterinario(cedulaVeterinario);
        return vetOpt.isPresent() && vetOpt.get().estaDisponible(fecha, hora);
    }

    // Métodos auxiliares
    /** Devuelve el índice de un propietario en la lista. */
    private int encontrarIndicePropietario(String cedula) {
        for (int i = 0; i < propietarios.size(); i++) {
            if (propietarios.get(i).getCedula().equals(cedula)) {
                return i;
            }
        }
        return -1;
    }

    /** Devuelve el índice de una mascota en la lista. */
    private int encontrarIndiceMascota(String id) {
        for (int i = 0; i < mascotas.size(); i++) {
            if (mascotas.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /** Devuelve el índice de un veterinario en la lista. */
    private int encontrarIndiceVeterinario(String cedula) {
        for (int i = 0; i < veterinarios.size(); i++) {
            if (veterinarios.get(i).getCedula().equals(cedula)) {
                return i;
            }
        }
        return -1;
    }

    /** Devuelve el índice de una cita en la lista. */
    private int encontrarIndiceCita(String id) {
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}