package co.edu.uniquindio.poo.veterinaria.model;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mascota {
    private String id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private Propietario propietario;
    @Builder.Default
    private List<Cita> historialCitas = new ArrayList<>();

    public void agregarCita(Cita cita) {
        historialCitas.add(cita);
    }

    public List<Cita> obtenerHistorialCompleto() {
        return new ArrayList<>(historialCitas);
    }

    public int obtenerTotalConsultas() {
        return (int) historialCitas.stream()
            .filter(cita -> cita.getConsulta() != null)
            .count();
    }
}


