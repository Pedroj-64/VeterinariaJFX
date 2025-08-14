package co.edu.uniquindio.poo.veterinaria.model;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Propietario {
    private String cedula;
    private String nombre;
    private String telefono;
    private String direccion;
    private String email;
    @Builder.Default
    private List<Mascota> mascotas = new ArrayList<>();

    public void agregarMascota(Mascota mascota) {
        mascotas.add(mascota);
    }

    public void removerMascota(Mascota mascota) {
        mascotas.remove(mascota);
    }

    public List<Mascota> obtenerMascotas() {
        return new ArrayList<>(mascotas);
    }

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