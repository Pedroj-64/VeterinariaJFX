package co.edu.uniquindio.poo.veterinaria.util;

import java.time.LocalDate;
import java.time.LocalTime;

import co.edu.uniquindio.poo.veterinaria.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor

public class DatosAgendamiento {
    private final Mascota mascota;
    private final Veterinario veterinario;
    private final LocalDate fecha;
    private final LocalTime hora;
    private final String motivo;



}
