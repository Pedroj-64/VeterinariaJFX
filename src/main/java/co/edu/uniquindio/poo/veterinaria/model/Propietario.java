package co.edu.uniquindio.poo.veterinaria.model;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

/**
 * Rcalse propietario de una o varias mascotas,
 * contiene información personal y la lista de mascotas que posee.
 */

public class Propietario {
    private String cedula;
    private String nombre;
    private String telefono;
    private String direccion;
    private String email;
    @Builder.Default
    private List<Mascota> mascotas = new ArrayList<>();

    /**
     * Agrega una mascota a la lista del propietario.
     *
     * @param mascota Mascota a registrar.
     */
    public void agregarMascota(Mascota mascota) {
        mascotas.add(mascota);
    }

    /**
     * Elimina una mascota de la lista del propietario.
     *
     * @param mascota Mascota a remover.
     */
    public void removerMascota(Mascota mascota) {
        mascotas.remove(mascota);
    }

    /**
     * Devuelve una copia de la lista de mascotas.
     *
     * @return Lista de mascotas del propietario.
     */
    public List<Mascota> obtenerMascotas() {
        return new ArrayList<>(mascotas);
    }
    
    /**
     * Obtiene todas las citas pendientes de las mascotas del propietario.
     *
     * @return Lista de citas pendientes.
     */
    public List<Cita> obtenerCitasPendientes() {
        List<Cita> citasPendientes = new ArrayList<>();
        for (Mascota mascota : mascotas) {
            for (Cita cita : mascota.getHistorialCitas()) {
                if (cita.estaPendiente()) {
                    citasPendientes.add(cita);
                }
            }
        }
        return citasPendientes;
    }
}