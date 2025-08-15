package co.edu.uniquindio.poo.veterinaria.model;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consulta {
    private Cita cita;
    private String diagnostico;
    private String tratamiento;
    private LocalDate fecha;

    /**
     * Genera un resumen en formato de texto con los datos principales
     * de la consulta.
     * @return Resumen con fecha, mascota, veterinario, diagnóstico y tratamiento.
     */
    
    public String getResumen() {
        return String.format("Consulta del %s%nMascota: %s%nVeterinario: %s%nDiagnóstico: %s%nTratamiento: %s",
            fecha, cita.getMascota().getNombre(), 
            cita.getVeterinario().getNombre(),
            diagnostico, tratamiento);
    }
}