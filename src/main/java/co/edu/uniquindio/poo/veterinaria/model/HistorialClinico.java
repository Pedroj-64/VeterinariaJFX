package co.edu.uniquindio.poo.veterinaria.model;

import java.util.ArrayList;
import java.util.List;

public class HistorialClinico {

    Mascota mascota;
    List<Consulta> consultas;

    public HistorialClinico(Mascota mascota){

        this.mascota = mascota;
        this.consultas = new ArrayList<>();

    }


    public void agregarConsulta(Consulta consulta) {
        if (consulta != null) {
            consultas.add(consulta);
        }
    }

        public List<Consulta> obtenerHistorial() {
        return consultas;
    }
        public String mostrarHistorial() {
        StringBuilder sb = new StringBuilder();
        sb.append("Historial clínico de ").append(mascota.getNombre()).append(":\n");
        for (Consulta c : consultas) {
            sb.append(c.getResumen()).append("\n");
        }
        return sb.toString();
    }
}