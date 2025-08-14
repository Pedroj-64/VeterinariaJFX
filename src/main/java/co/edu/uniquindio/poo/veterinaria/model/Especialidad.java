package co.edu.uniquindio.poo.veterinaria.model;

public enum Especialidad {
    ANIMALES_PEQUENOS("Animales Pequeños"),
    ANIMALES_GRANDES("Animales Grandes"),
    ANIMALES_SALVAJES("Animales Salvajes"),
    ANIMALES_CASEROS("Animales Caseros"),
    OTROS("Otros");

    private final String descripcion;

    Especialidad(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
