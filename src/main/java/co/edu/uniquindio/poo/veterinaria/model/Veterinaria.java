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

    public void inicializar(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        limpiarDatos();
    }
    
    public void limpiarDatos() {
        this.propietarios.clear();
        this.mascotas.clear();
        this.veterinarios.clear();
        this.citas.clear();
    }

    // CRUD Propietario
    public void registrarPropietario(Propietario propietario) {
        validarPropietarioExistente(propietario.getCedula());
        propietarios.add(propietario);
    }

    public Optional<Propietario> buscarPropietario(String cedula) {
        return propietarios.stream()
                .filter(p -> p.getCedula().equals(cedula))
                .findFirst();
    }

    public void actualizarPropietario(Propietario propietario) {
        int index = encontrarIndicePropietario(propietario.getCedula());
        if (index != -1) {
            propietarios.set(index, propietario);
        }
    }

    public void eliminarPropietario(String cedula) {
        propietarios.removeIf(p -> p.getCedula().equals(cedula));
    }

    // CRUD Mascota
    public void registrarMascota(Mascota mascota) {
        validarMascotaExistente(mascota.getId());
        mascotas.add(mascota);
    }

    public Optional<Mascota> buscarMascota(String id) {
        return mascotas.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    public void actualizarMascota(Mascota mascota) {
        int index = encontrarIndiceMascota(mascota.getId());
        if (index != -1) {
            mascotas.set(index, mascota);
        }
    }

    public void eliminarMascota(String id) {
        mascotas.removeIf(m -> m.getId().equals(id));
    }

    // CRUD Veterinario
    public void registrarVeterinario(Veterinario veterinario) {
        validarVeterinarioExistente(veterinario.getCedula());
        veterinarios.add(veterinario);
    }

    public Optional<Veterinario> buscarVeterinario(String cedula) {
        return veterinarios.stream()
                .filter(v -> v.getCedula().equals(cedula))
                .findFirst();
    }

    public void actualizarVeterinario(Veterinario veterinario) {
        int index = encontrarIndiceVeterinario(veterinario.getCedula());
        if (index != -1) {
            veterinarios.set(index, veterinario);
        }
    }

    public void eliminarVeterinario(String cedula) {
        veterinarios.removeIf(v -> v.getCedula().equals(cedula));
    }

    // CRUD Citas
    public void agendarCita(Cita cita) {
        validarDisponibilidadHorario(cita);
        citas.add(cita);
        cita.getMascota().agregarCita(cita);
        cita.getVeterinario().agregarCita(cita); // Agrega la cita al veterinario y actualiza sus horarios
    }

    public Optional<Cita> buscarCita(String id) {
        return citas.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public void actualizarCita(Cita cita) {
        int index = encontrarIndiceCita(cita.getId());
        if (index != -1) {
            citas.set(index, cita);
        }
    }

    public void cancelarCita(String id) {
        citas.removeIf(c -> c.getId().equals(id));
    }

    // Métodos de consulta
    public List<Cita> obtenerCitas() {
        return new ArrayList<>(citas);
    }

    public List<Cita> obtenerCitasPorFecha(LocalDate fecha) {
        return citas.stream()
                .filter(c -> c.getFechaHora().toLocalDate().equals(fecha))
                .collect(Collectors.toList());
    }

    public List<Cita> obtenerCitasVeterinario(String cedulaVeterinario, LocalDate fecha) {
        return citas.stream()
                .filter(c -> c.getVeterinario().getCedula().equals(cedulaVeterinario) 
                        && c.getFechaHora().toLocalDate().equals(fecha))
                .collect(Collectors.toList());
    }

    public List<LocalTime> obtenerHorariosDisponibles(String cedulaVeterinario, LocalDate fecha) {
        Optional<Veterinario> vetOpt = buscarVeterinario(cedulaVeterinario);
        if (vetOpt.isPresent()) {
            return vetOpt.get().getHorariosDisponibles(fecha);
        }
        return new ArrayList<>();
    }

    // Métodos de validación
    private void validarPropietarioExistente(String cedula) {
        if (buscarPropietario(cedula).isPresent()) {
            throw new IllegalArgumentException("Ya existe un propietario con la cédula: " + cedula);
        }
    }

    private void validarMascotaExistente(String id) {
        if (buscarMascota(id).isPresent()) {
            throw new IllegalArgumentException("Ya existe una mascota con el ID: " + id);
        }
    }

    private void validarVeterinarioExistente(String cedula) {
        if (buscarVeterinario(cedula).isPresent()) {
            throw new IllegalArgumentException("Ya existe un veterinario con la cédula: " + cedula);
        }
    }

    private void validarDisponibilidadHorario(Cita cita) {
        Veterinario veterinario = cita.getVeterinario();
        LocalDateTime fechaHora = cita.getFechaHora();
        if (!veterinario.estaDisponible(fechaHora.toLocalDate(), fechaHora.toLocalTime())) {
            throw new IllegalArgumentException("El horario seleccionado no está disponible");
        }
    }

    private boolean esHorarioDisponible(String cedulaVeterinario, LocalDate fecha, LocalTime hora) {
        Optional<Veterinario> vetOpt = buscarVeterinario(cedulaVeterinario);
        return vetOpt.isPresent() && vetOpt.get().estaDisponible(fecha, hora);
    }

    // Métodos auxiliares
    private int encontrarIndicePropietario(String cedula) {
        for (int i = 0; i < propietarios.size(); i++) {
            if (propietarios.get(i).getCedula().equals(cedula)) {
                return i;
            }
        }
        return -1;
    }

    private int encontrarIndiceMascota(String id) {
        for (int i = 0; i < mascotas.size(); i++) {
            if (mascotas.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private int encontrarIndiceVeterinario(String cedula) {
        for (int i = 0; i < veterinarios.size(); i++) {
            if (veterinarios.get(i).getCedula().equals(cedula)) {
                return i;
            }
        }
        return -1;
    }

    private int encontrarIndiceCita(String id) {
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}

