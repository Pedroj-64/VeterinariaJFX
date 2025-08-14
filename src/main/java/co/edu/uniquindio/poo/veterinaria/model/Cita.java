package co.edu.uniquindio.poo.veterinaria.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {
    private String id;
    private Mascota mascota;
    private Veterinario veterinario;
    private LocalDateTime fechaHora;
    private String motivo;
    @Builder.Default
    private EstadoCita estado = EstadoCita.PENDIENTE;
    private Consulta consulta;

    public void agregarConsulta(String diagnostico, String tratamiento) {
        this.consulta = Consulta.builder()
            .cita(this)
            .diagnostico(diagnostico)
            .tratamiento(tratamiento)
            .fecha(LocalDate.now())
            .build();
        this.estado = EstadoCita.COMPLETADA;
    }

    public boolean estaPendiente() {
        return estado == EstadoCita.PENDIENTE;
    }

    public void cancelar() {
        this.estado = EstadoCita.CANCELADA;
    }
}

enum EstadoCita {
    PENDIENTE,
    COMPLETADA,
    CANCELADA
}