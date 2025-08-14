package co.edu.uniquindio.poo.veterinaria.viewController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.controller.MenuVeterinarioController;
import co.edu.uniquindio.poo.veterinaria.model.Mascota;
import co.edu.uniquindio.poo.veterinaria.model.Veterinario;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MenuVeterinarioViewController {

    @FXML
    private Button btn_enviarDiagnostico;

    @FXML
    private Button btn_descargarHistorial;

    @FXML
    private Button btn_volver;

    @FXML
    private ListView<Mascota> listaMascotas;

    @FXML
    private TextArea txtDiagnostico;

    @FXML
    private TextField txtDueno;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtMotivo;

    @FXML
    private TextField txtNombreMascota;

    @FXML
    private TextArea txtTratamiento;

    private MenuVeterinarioController controller;

    @FXML
    void initialize() {
        try {
            // Obtener el veterinario actual desde App
            Veterinario veterinarioActual = App.getVeterinaria().getVeterinarios().stream()
                    .filter(v -> v.getCedula().equals("123"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No se encontró el veterinario actual"));

            controller = MenuVeterinarioController.getInstance(veterinarioActual);

            configurarListaMascotas();
            cargarDatos();
        } catch (Exception e) {
            App.showAlert("Error", "Error al inicializar: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    void descargarHistorial(ActionEvent event) {
        try {
            // Validar que haya una mascota seleccionada
            Mascota mascotaSeleccionada = listaMascotas.getSelectionModel().getSelectedItem();
            if (mascotaSeleccionada == null) {
                App.showAlert("Error", "Debe seleccionar una mascota", AlertType.ERROR);
                return;
            }

            // Generar el historial médico
            String historial = controller.generarHistorialMedico(mascotaSeleccionada);

            // Crear el nombre del archivo
            String nombreArchivo = String.format("historial_%s_%s.txt", 
                mascotaSeleccionada.getNombre().replaceAll("\\s+", "_").toLowerCase(),
                LocalDate.now());

            // Crear y escribir el archivo
            Path path = Paths.get(System.getProperty("user.home"), "Downloads", nombreArchivo);
            Files.writeString(path, historial);

            App.showAlert("Éxito", 
                "Historial descargado exitosamente en: " + path.toString(), 
                AlertType.INFORMATION);

        } catch (Exception e) {
            App.showAlert("Error", "Error al descargar el historial: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void configurarListaMascotas() {
        // Configurar la visualización de las mascotas en la lista
        listaMascotas.setCellFactory(param -> new ListCell<Mascota>() {
            @Override
            protected void updateItem(Mascota item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre());
                }
            }
        });

        // Listener para cuando se selecciona una mascota
        listaMascotas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actualizarDatosMascota(newVal);
            }
        });
    }

    private void actualizarDatosMascota(Mascota mascota) {
        // Autocompletar campos con información de la mascota
        txtNombreMascota.setText(mascota.getNombre());
        txtDueno.setText(mascota.getPropietario().getNombre());
        txtEdad.setText(String.valueOf(mascota.getEdad()));

        // Obtener el motivo de la cita
        controller.obtenerCitaMascota(mascota).ifPresent(cita -> {
            txtMotivo.setText(cita.getMotivo());
        });

        // Limpiar campos de diagnóstico para nueva entrada
        txtDiagnostico.clear();
        txtTratamiento.clear();
    }

    private void cargarDatos() {
        var pacientes = controller.obtenerPacientesDelDia();
        listaMascotas.setItems(FXCollections.observableArrayList(pacientes));
    }

    @FXML
    void enviarDiagnostico(ActionEvent event) {
        try {
            // Validar que haya una mascota seleccionada
            Mascota mascotaSeleccionada = listaMascotas.getSelectionModel().getSelectedItem();
            if (mascotaSeleccionada == null) {
                App.showAlert("Error", "Debe seleccionar una mascota", AlertType.ERROR);
                return;
            }

            // Obtener los datos del diagnóstico
            String diagnostico = txtDiagnostico.getText();
            String tratamiento = txtTratamiento.getText();

            // Validar campos requeridos
            if (diagnostico.trim().isEmpty() || tratamiento.trim().isEmpty()) {
                App.showAlert("Error", "Debe completar el diagnóstico y el tratamiento", AlertType.ERROR);
                return;
            }

            // Registrar la consulta a través del controlador
            controller.registrarConsulta(mascotaSeleccionada, diagnostico, tratamiento);

            // Limpiar campos y actualizar lista
            limpiarCampos();
            cargarDatos();

            // Mostrar mensaje de éxito
            App.showAlert("Éxito", "Consulta registrada correctamente", AlertType.INFORMATION);

        } catch (Exception e) {
            App.showAlert("Error", e.getMessage(), AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        txtNombreMascota.clear();
        txtDueno.clear();
        txtEdad.clear();
        txtMotivo.clear();
        txtDiagnostico.clear();
        txtTratamiento.clear();
        listaMascotas.getSelectionModel().clearSelection();
    }

    @FXML
    void volver(ActionEvent event) {
        App.loadScene("login", 640, 480);
    }

}
