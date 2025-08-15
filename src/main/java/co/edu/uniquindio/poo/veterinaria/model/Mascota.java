package co.edu.uniquindio.poo.veterinaria.model;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

/** Representa una Mascota en la veterinaria 
 * con su informacion básica y un historial de citas.
 */
public class Mascota {
    private String id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private Propietario propietario;
    @Builder.Default
    private List<Cita> historialCitas = new ArrayList<>();

    /**
     * Agrega una cita al historial de la mascota.
     *
     * @param cita Cita a registrar.
     */
    public void agregarCita(Cita cita) {
        historialCitas.add(cita);
    }

    /**
     * Devuelve una copia del historial completo de citas.
     *
     * @return Lista de citas de la mascota.
     */
    public List<Cita> obtenerHistorialCompleto() {
        return new ArrayList<>(historialCitas);
    }

    /**
     * Calcula el total de consultas registradas en las citas de la mascota.
     *
     * @return Número de consultas realizadas.
     */
    public int obtenerTotalConsultas() {
        return (int) historialCitas.stream()
            .filter(cita -> cita.getConsulta() != null)
            .count();
    }
}