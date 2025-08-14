package co.edu.uniquindio.poo.veterinaria.model;

import java.time.LocalDate;
import java.util.List;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
public class Veterinaria {

    String nombre;
    String direccion;
    List<Propietario> propietarios;
    List<Mascota> mascotas;
    List<Veterinario> veterinarios;
    List<Consulta> consultas;

// método para agregar una mascota a la lista:
 public void registrarMascota(Mascota mascota){
    mascotas.add(mascota); // Agrega una mascota a la lista.
 }
// método para agregar un veterinario a la lista:
 public void registrarVeterinario(Veterinario veterinario){
    veterinarios.add(veterinario); // Agrega un veterinario a la lista.
 }
 //método para agregarr una onsculta a la lista:
 public void agregarConsulta(Consulta consulta){
    consultas.add(consulta); // Agrega una consutla.
 }
// metodo para buscar citas por día:
 public list<Consulta> buscarCitasPorDia(LocalDate fecha){
    List<Consulta> citasDelDia = new Arraylist<>();
    for (Consulta consulta : consultas){
        if (consulta.getFecha().equals(fecha)){
            citasDelDia.add(consulta);
        }
    }
    return (list<Consulta>) citasDelDia; //Devulve las citas del día.
    }
}
