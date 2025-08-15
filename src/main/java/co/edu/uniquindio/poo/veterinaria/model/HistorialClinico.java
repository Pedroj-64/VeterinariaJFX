package co.edu.uniquindio.poo.veterinaria.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el historial clínico de una mascota,
 * compuesto por las consultas realizadas.
 */
public class HistorialClinico {

    Mascota mascota;
    List<Consulta> consultas;

    /**
     * Crea un historial clínico vacío para una mascota.
     * @param mascota Mascota asociada al historial.
     */
    public HistorialClinico(Mascota mascota){

        this.mascota = mascota;
        this.consultas = new ArrayList<>();

    }

    /**
     * Agrega una consulta al historial.
     *
     * @param consulta Consulta a registrar.
     */
    public void agregarConsulta(Consulta consulta) {
        if (consulta != null) {
            consultas.add(consulta);
        }
    }
    /**
     * Devuelve la lista de consultas registradas.
     *
     * @return Lista de consultas.
     */
        public List<Consulta> obtenerHistorial() {
        return consultas;
    }
    
    /**
     * Muestra el historial clínico en formato de texto.
     *
     * @return Resumen con las consultas de la mascota.
     */
        public String mostrarHistorial() {
        StringBuilder sb = new StringBuilder();
        sb.append("Historial clínico de ").append(mascota.getNombre()).append(":\n");
        for (Consulta c : consultas) {
            sb.append(c.getResumen()).append("\n");
        }
        return sb.toString();
    }
}