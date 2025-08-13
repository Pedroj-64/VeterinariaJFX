package co.edu.uniquindio.poo.veterinaria.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
@Getter
@Setter
@Data
public class Cita {

    LocalDateTime fechaHora;
    String motivoCita;
    UUID codigoCita;

}