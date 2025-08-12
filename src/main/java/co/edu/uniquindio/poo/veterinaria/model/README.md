# Capa Modelo (Model)

Esta capa representa el núcleo de la lógica de negocio y los datos de la aplicación veterinaria.

## Responsabilidades

- Contiene las clases que representan las entidades del negocio (Mascota, Cliente, Cita, Diagnóstico, etc.)
- Implementa la lógica de negocio y validaciones
- Maneja el acceso y persistencia de datos
- No tiene dependencias con las capas de Vista o Controlador
- Notifica los cambios en los datos a través de patrones de observador

## Importancia en MVC

El Modelo es la capa fundamental que mantiene los datos y reglas del negocio independientes de la presentación y la lógica de control, permitiendo que la aplicación sea más mantenible y escalable.
