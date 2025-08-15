package co.edu.uniquindio.poo.veterinaria.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

/**
 * Clase de  una cita veterinaria de una mascota con un veterinario.
 * Contiene la fecha, motivo, estado y una consulta asociada a la cita.
 */

public class Cita {
    private String id;
    private Mascota mascota;
    private Veterinario veterinario;
    private LocalDateTime fechaHora;
    private String motivo;
    @Builder.Default
    private EstadoCita estado = EstadoCita.PENDIENTE;
    private Consulta consulta;


    /*
     * Agrega una consulta médica a la cita actual, registrando diagnóstico,
     * tratamiento y fecha. Al hacerlo, el estado de la cita cambia a COMPLETADA.
     * */

    public void agregarConsulta(String diagnostico, String tratamiento) {
        this.consulta = Consulta.builder()
            .cita(this)
            .diagnostico(diagnostico)
            .tratamiento(tratamiento)
            .fecha(LocalDate.now())
            .build();
        this.estado = EstadoCita.COMPLETADA;
    }

    /*
     * Verifica si la cita se encuentra en estado pendiente.
     * @return true si la cita está pendiente, false en caso contrario.
     */
    public boolean estaPendiente() {
        return estado == EstadoCita.PENDIENTE;
    }

    /*
     * Cancela la cita, cambiando su estado a cANCELADA.
     */
    public void cancelar() {
        this.estado = EstadoCita.CANCELADA;
    }
}
/*
 * Enum que representa los posibles estados de una cita veterinaria.
 */
enum EstadoCita {
    PENDIENTE,
    COMPLETADA,
    CANCELADA
}