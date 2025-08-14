package co.edu.uniquindio.poo.veterinaria.model;
import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor

public class Consulta {

    LocalDate fecha;
    LocalDate hora;
    String Diagnostico;
    String Tratamiento;
    Mascota mascota;
    Veterinario veterinario;
    public Object getResumen() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResumen'");
    }

}