package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@DisplayName("Pruebas de la clase Veterinaria")
class VeterinariaTest {
    private Veterinaria veterinaria;
    private Veterinario veterinario;
    private Propietario propietario;
    private Mascota mascota;
    
    @Test
    @DisplayName("Verificar limpieza y estado inicial de la veterinaria")
    void testEstadoInicial() {
        // Verificar estado inicial
        assertEquals("Veterinaria Test", veterinaria.getNombre());
        assertEquals("Calle Test #123", veterinaria.getDireccion());
        assertTrue(veterinaria.getVeterinarios().isEmpty());
        assertTrue(veterinaria.getPropietarios().isEmpty());
        assertTrue(veterinaria.getMascotas().isEmpty());
        assertTrue(veterinaria.getCitas().isEmpty());
        
        // Registrar algunos datos
        veterinaria.registrarVeterinario(veterinario);
        veterinaria.registrarPropietario(propietario);
        assertFalse(veterinaria.getVeterinarios().isEmpty());
        assertFalse(veterinaria.getPropietarios().isEmpty());
        
        // Reinicializar y verificar limpieza
        veterinaria.inicializar("Nuevo Nombre", "Nueva Dirección");
        assertEquals("Nuevo Nombre", veterinaria.getNombre());
        assertEquals("Nueva Dirección", veterinaria.getDireccion());
        assertTrue(veterinaria.getVeterinarios().isEmpty());
        assertTrue(veterinaria.getPropietarios().isEmpty());
        assertTrue(veterinaria.getMascotas().isEmpty());
        assertTrue(veterinaria.getCitas().isEmpty());
    }

    @BeforeEach
    void setUp() {
        // Obtener y limpiar la instancia de Veterinaria para cada prueba
        veterinaria = Veterinaria.getInstance();
        veterinaria.inicializar("Veterinaria Test", "Calle Test #123");
        
        // Verificar que la veterinaria está vacía
        assertTrue(veterinaria.getVeterinarios().isEmpty(), "La lista de veterinarios debería estar vacía");
        assertTrue(veterinaria.getPropietarios().isEmpty(), "La lista de propietarios debería estar vacía");
        assertTrue(veterinaria.getMascotas().isEmpty(), "La lista de mascotas debería estar vacía");
        assertTrue(veterinaria.getCitas().isEmpty(), "La lista de citas debería estar vacía");
        
        // Preparar datos de prueba usando builders
        propietario = Propietario.builder()
            .cedula("98765")
            .nombre("Dueño Test")
            .telefono("3001234567")
            .email("dueno@test.com")
            .build();
            
        veterinario = Veterinario.builder()
            .cedula("12345")
            .nombre("Dr. Test")
            .especialidad("Animales Pequeños")
            .email("vet@test.com")
            .telefono("3009876543")
            .build();
            
        mascota = Mascota.builder()
            .id("M001")
            .nombre("Firulais")
            .edad(3)
            .especie("Perro")
            .raza("Labrador")
            .propietario(propietario)
            .build();
    }

    @Test
    @DisplayName("Registrar veterinario exitosamente y validar duplicados")
    void testRegistrarVeterinario() {
        // Prueba registro exitoso
        assertDoesNotThrow(() -> veterinaria.registrarVeterinario(veterinario));
        
        var vetEncontrado = veterinaria.buscarVeterinario("12345");
        assertTrue(vetEncontrado.isPresent());
        assertEquals("Dr. Test", vetEncontrado.get().getNombre());
        assertEquals("Animales Pequeños", vetEncontrado.get().getEspecialidad());
        
        // Prueba registro duplicado
        var exception = assertThrows(IllegalArgumentException.class, 
            () -> veterinaria.registrarVeterinario(veterinario));
        assertTrue(exception.getMessage().contains("Ya existe un veterinario"));
    }

    @Test
    @DisplayName("Agendar cita y verificar datos")
    void testAgendarCita() {
        // Preparar
        veterinaria.registrarVeterinario(veterinario);
        veterinaria.registrarPropietario(propietario);
        veterinaria.registrarMascota(mascota);
        
        var fecha = LocalDate.now().plusDays(1);
        var hora = LocalTime.of(14, 0);
        var fechaHora = LocalDateTime.of(fecha, hora);
        
        // Verificar que el horario está disponible antes de agendar
        assertTrue(veterinario.getHorariosDisponibles(fecha).contains(hora), 
            "El horario debería estar disponible antes de agendar");
        
        var cita = Cita.builder()
            .id("C001")
            .fechaHora(fechaHora)
            .mascota(mascota)
            .veterinario(veterinario)
            .motivo("Consulta general")
            .build();
        
        // Ejecutar y verificar
        assertDoesNotThrow(() -> veterinaria.agendarCita(cita));
        
        var citaAgendada = veterinaria.buscarCita("C001");
        assertTrue(citaAgendada.isPresent(), "La cita debería estar registrada");
        assertEquals(EstadoCita.PENDIENTE, citaAgendada.get().getEstado());
        assertEquals("Firulais", citaAgendada.get().getMascota().getNombre());
        assertEquals("Dr. Test", citaAgendada.get().getVeterinario().getNombre());
        
        // Verificar que el horario ya no está disponible
        assertFalse(veterinario.getHorariosDisponibles(fecha).contains(hora), 
            "El horario no debería estar disponible después de agendar");
    }

    @Test
    @DisplayName("Obtener citas de un veterinario en una fecha específica")
    void testObtenerCitasVeterinario() {
        // Preparar
        veterinaria.registrarVeterinario(veterinario);
        veterinaria.registrarPropietario(propietario);
        veterinaria.registrarMascota(mascota);
        
        var fecha = LocalDate.now();
        var hora1 = LocalTime.of(9, 0);  // Cambiado a 9:00 AM
        var hora2 = LocalTime.of(10, 0);  // Cambiado a 10:00 AM
        var fechaHora1 = LocalDateTime.of(fecha, hora1);
        var fechaHora2 = LocalDateTime.of(fecha, hora2);
        
        // Verificar disponibilidad inicial de horarios
        assertTrue(veterinario.getHorariosDisponibles(fecha).contains(hora1));
        assertTrue(veterinario.getHorariosDisponibles(fecha).contains(hora2));
        
        var cita1 = Cita.builder()
            .id("C001")
            .fechaHora(fechaHora1)
            .mascota(mascota)
            .veterinario(veterinario)
            .motivo("Consulta 1")
            .build();
        
        var cita2 = Cita.builder()
            .id("C002")
            .fechaHora(fechaHora2)
            .mascota(mascota)
            .veterinario(veterinario)
            .motivo("Consulta 2")
            .build();
        
        assertDoesNotThrow(() -> veterinaria.agendarCita(cita1), "La primera cita debería agendarse sin problemas");
        assertDoesNotThrow(() -> veterinaria.agendarCita(cita2), "La segunda cita debería agendarse sin problemas");
        
        // Ejecutar
        var citasDelDia = veterinaria.obtenerCitasVeterinario(veterinario.getCedula(), fecha);
        
        // Verificar
        assertEquals(2, citasDelDia.size(), "Deberían haber dos citas agendadas");
        assertTrue(citasDelDia.stream().map(Cita::getId).collect(Collectors.toList()).containsAll(Arrays.asList("C001", "C002")));
        assertTrue(citasDelDia.stream().allMatch(c -> c.getVeterinario().getCedula().equals("12345")));
        assertTrue(citasDelDia.stream().allMatch(c -> c.getFechaHora().toLocalDate().equals(fecha)));
        
        // Verificar que los horarios ya no están disponibles
        assertFalse(veterinario.getHorariosDisponibles(fecha).contains(hora1));
        assertFalse(veterinario.getHorariosDisponibles(fecha).contains(hora2));
    }

    @Test
    @DisplayName("Validar disponibilidad de horarios para citas")
    void testValidarDisponibilidadHorario() {
        // Preparar
        veterinaria.registrarVeterinario(veterinario);
        veterinaria.registrarPropietario(propietario);
        veterinaria.registrarMascota(mascota);
        
        var fecha = LocalDate.now();
        var hora = LocalTime.of(9, 0);  // Cambiado a 9:00 AM
        var fechaHora = LocalDateTime.of(fecha, hora);
        
        // Verificar disponibilidad inicial
        assertTrue(veterinario.getHorariosDisponibles(fecha).contains(hora),
            "El horario debería estar disponible inicialmente");
        
        var cita1 = Cita.builder()
            .id("C001")
            .fechaHora(fechaHora)
            .mascota(mascota)
            .veterinario(veterinario)
            .motivo("Consulta 1")
            .build();
        
        // Primera cita debe agendarse sin problemas
        assertDoesNotThrow(() -> veterinaria.agendarCita(cita1),
            "La primera cita debería agendarse sin problemas");
        
        // Verificar que el horario ya no está disponible
        assertFalse(veterinario.getHorariosDisponibles(fecha).contains(hora),
            "El horario no debería estar disponible después de agendar la primera cita");
        
        // Intentar agendar otra cita en el mismo horario
        var cita2 = Cita.builder()
            .id("C002")
            .fechaHora(fechaHora)
            .mascota(mascota)
            .veterinario(veterinario)
            .motivo("Consulta 2")
            .build();
        
        // Debe lanzar excepción por horario no disponible
        var exception = assertThrows(IllegalArgumentException.class, 
            () -> veterinaria.agendarCita(cita2),
            "Debería lanzar excepción al intentar agendar en un horario ocupado");
        assertTrue(exception.getMessage().contains("horario seleccionado no está disponible"));
        
        // Verificar que la primera cita sigue registrada
        var citasDelDia = veterinaria.obtenerCitasVeterinario(veterinario.getCedula(), fecha);
        assertEquals(1, citasDelDia.size(), "Solo debería haber una cita registrada");
        assertEquals("C001", citasDelDia.get(0).getId());
    }
}
