package co.edu.uniquindio.poo.veterinaria.model;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor

public class Veterinario {

    String nombre;
    String idLicencia;
    String especialidad;
    String telefono;


    public void agregarVeterinario(String nombre, String idLicencia, String especialidad){

    }

    public void agregarPropietario(String nombre, String id, String telefono, String direccion, String email){

    }

    public void agregarMascota(String nombre, byte edad, String idVeterinaria){

    }

    public void verificarMascotaUnicoPropietario( String idVeterinaria, String nombreMascota){

    }
}
