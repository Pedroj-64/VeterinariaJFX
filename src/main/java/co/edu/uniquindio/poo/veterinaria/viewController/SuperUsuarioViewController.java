package co.edu.uniquindio.poo.veterinaria.viewController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.stream.Collectors;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.model.Especialidad;
import co.edu.uniquindio.poo.veterinaria.model.Mascota;
import co.edu.uniquindio.poo.veterinaria.model.Veterinario;
import co.edu.uniquindio.poo.veterinaria.model.Propietario;
import co.edu.uniquindio.poo.veterinaria.model.Cita;
import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;
import co.edu.uniquindio.poo.veterinaria.util.DatosAgendamiento;
import javafx.scene.control.Alert.AlertType;
import co.edu.uniquindio.poo.veterinaria.controller.SuperUsuarioController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SuperUsuarioViewController {

    @FXML
    private Button btn_agendarCita;

    @FXML
    private Button btn_registrarMascota;

    @FXML
    private Button btn_registrarPropietario;

    @FXML
    private Button btn_registrarVeterinaria;

    @FXML
    private Button btn_volver;

    @FXML
    private ComboBox<String> comboHorario;

    @FXML
    private ComboBox<Especialidad> comboEspecialidad;

    @FXML
    private ComboBox<Mascota> comboMascota;

    @FXML
    private ComboBox<Propietario> comboPropietario;

    @FXML
    private ComboBox<Propietario> comboPropietarioMascota;

    @FXML
    private ComboBox<Veterinario> comboVeterinario;

    @FXML
    private DatePicker date_Cita;

    @FXML
    private Label headerTitle;

    @FXML
    private TableColumn<Veterinaria, String> tbc_Especialidad;

    @FXML
    private TableColumn<Mascota, String> tbc_Mascota;

    @FXML
    private TableColumn<Propietario, String> tbc_cedula;

    @FXML
    private TableColumn<Mascota, String> tbc_cedulaVeterinario;

    @FXML
    private TableColumn<Propietario, String> tbc_email;

    @FXML
    private TableColumn<Mascota, String> tbc_especie;

    @FXML
    private TableColumn<Cita, LocalDate> tbc_fecha;

    @FXML
    private TableColumn<Cita, String> tbc_hora;

    @FXML
    private TableColumn<Veterinario, String> tbc_nombre;

    @FXML
    private TableColumn<Propietario, String> tbc_nombrePropietarios;

    @FXML
    private TableColumn<Veterinario, String> tbc_nombreVeterinario;

    @FXML
    private TableColumn<Propietario, String> tbc_propietario;

    @FXML
    private TableColumn<Mascota, String> tbc_raza;

    @FXML
    private TableColumn<Propietario, String> tbc_telefono;

    @FXML
    private TableColumn<Veterinario, String> tbc_telefonoVeterinario;

    @FXML
    private TableColumn<Cita, Veterinario> tbc_veterinario;

    @FXML
    private TableView<Cita> tbl_citas;

    @FXML
    private TableView<Mascota> tbl_mascota;

    @FXML
    private TableView<Propietario> tbl_propietarios;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCedulaVet;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmailVet;

    @FXML
    private TextField txtEspecialidad;

    @FXML
    private TextField txtEspecie;

    @FXML
    private TextField txtMotivo;

    @FXML
    private TextField txtNombreMascota;

    @FXML
    private TextField txtNombrePropietario;

    private SuperUsuarioController controller = SuperUsuarioController.getInstance();

    @FXML
    void initialize() {
        try {
            configurarTablas();
            configurarComboBoxes();
            cargarDatos();
            configurarListeners();
        } catch (Exception e) {
            App.showAlert("Error", "Error al inicializar la interfaz: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void configurarComboBoxes() {
        // Configurar ComboBox de horarios
        comboHorario.setPromptText("Seleccione un horario");
        
        // Configurar ComboBox de especialidades
        comboEspecialidad.setPromptText("Seleccione una especialidad");
        
        // Configurar ComboBox de mascotas
        comboMascota.setPromptText("Seleccione una mascota");
        comboMascota.setCellFactory(param -> new javafx.scene.control.ListCell<Mascota>() {
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
        
        // Configurar ComboBox de propietarios
        comboPropietario.setPromptText("Seleccione un propietario");
        comboPropietarioMascota.setPromptText("Seleccione un propietario");
        comboPropietario.setCellFactory(param -> new javafx.scene.control.ListCell<Propietario>() {
            @Override
            protected void updateItem(Propietario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre() + " - " + item.getCedula());
                }
            }
        });
        comboPropietarioMascota.setCellFactory(comboPropietario.getCellFactory());
        
        // Configurar ComboBox de veterinarios
        comboVeterinario.setPromptText("Seleccione un veterinario");
        comboVeterinario.setCellFactory(param -> new javafx.scene.control.ListCell<Veterinario>() {
            @Override
            protected void updateItem(Veterinario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre() + " - " + item.getEspecialidad());
                }
            }
        });
    }

    private void configurarListeners() {
        // Listeners para actualizar horarios cuando cambie el veterinario o la fecha
        comboVeterinario.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actualizarHorarios();
            }
        });
        
        date_Cita.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actualizarHorarios();
            }
        });
        
        // Listener para filtrar mascotas cuando cambie el propietario
        comboPropietarioMascota.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actualizarMascotasPorPropietario();
            }
        });
    }

    private void configurarTablas() {
        // Configurar columnas de la tabla de propietarios
        tbc_cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        tbc_nombrePropietarios.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbc_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tbc_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbc_propietario.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        // Configurar columnas de la tabla de mascotas
        tbc_Mascota.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbc_especie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        tbc_raza.setCellValueFactory(new PropertyValueFactory<>("raza"));
        
        // Configurar columnas de la tabla de citas
        tbc_veterinario.setCellValueFactory(new PropertyValueFactory<>("veterinario"));
        tbc_fecha.setCellValueFactory(cell -> javafx.beans.binding.Bindings.createObjectBinding(
            () -> cell.getValue().getFechaHora().toLocalDate(),
            javafx.beans.binding.Bindings.createObjectBinding(() -> cell.getValue().getFechaHora())
        ));
        tbc_hora.setCellValueFactory(cell -> javafx.beans.binding.Bindings.createStringBinding(
            () -> cell.getValue().getFechaHora().toLocalTime().toString(),
            javafx.beans.binding.Bindings.createObjectBinding(() -> cell.getValue().getFechaHora())
        ));
    }

    private void cargarDatos() {
        try {
            var propietarios = controller.obtenerPropietarios();
            var mascotas = controller.obtenerMascotas();
            var citas = controller.obtenerCitas();
            var veterinarios = controller.obtenerVeterinarios();
            var especialidades = controller.obtenerEspecialidades();
            
            // Cargar datos en las tablas
            tbl_propietarios.setItems(FXCollections.observableArrayList(propietarios != null ? propietarios : FXCollections.emptyObservableList()));
            tbl_mascota.setItems(FXCollections.observableArrayList(mascotas != null ? mascotas : FXCollections.emptyObservableList()));
            tbl_citas.setItems(FXCollections.observableArrayList(citas != null ? citas : FXCollections.emptyObservableList()));
            
            // Cargar datos en los ComboBox
            comboPropietario.setItems(FXCollections.observableArrayList(propietarios != null ? propietarios : FXCollections.emptyObservableList()));
            comboPropietarioMascota.setItems(FXCollections.observableArrayList(propietarios != null ? propietarios : FXCollections.emptyObservableList()));
            comboVeterinario.setItems(FXCollections.observableArrayList(veterinarios != null ? veterinarios : FXCollections.emptyObservableList()));
            comboEspecialidad.setItems(FXCollections.observableArrayList(especialidades != null ? especialidades : FXCollections.emptyObservableList()));

            // Limpiar selecciones
            comboHorario.getSelectionModel().clearSelection();
            comboMascota.getSelectionModel().clearSelection();
            comboPropietario.getSelectionModel().clearSelection();
            comboPropietarioMascota.getSelectionModel().clearSelection();
            comboVeterinario.getSelectionModel().clearSelection();
            comboEspecialidad.getSelectionModel().clearSelection();
            
        } catch (Exception e) {
            App.showAlert("Error", "Error al cargar los datos: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void actualizarHorarios() {
        comboHorario.getItems().clear();
        
        Veterinario veterinario = comboVeterinario.getValue();
        LocalDate fecha = date_Cita.getValue();
        
        if (veterinario != null && fecha != null) {
            var horarios = veterinario.getHorariosDisponibles(fecha);
            comboHorario.setItems(FXCollections.observableArrayList(
                horarios.stream()
                    .map(LocalTime::toString)
                    .collect(Collectors.toList())
            ));
        }
    }

    private void actualizarMascotasPorPropietario() {
        try {
            comboMascota.getItems().clear();
            Propietario propietario = comboPropietarioMascota.getValue();
            if (propietario != null) {
                comboMascota.setItems(FXCollections.observableArrayList(
                    controller.obtenerMascotasPorPropietario(propietario)));
            }
        } catch (Exception e) {
            App.showAlert("Error", "Error al actualizar las mascotas: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private TextField txtNombreVet;

    @FXML
    private TextField txtRaza;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtTelefonoVet;

    // =====================================
    // Eventos de la interfaz
    // =====================================

    @FXML
    void agendarCita(ActionEvent event) {
        try {
            // Recolectar datos
            var datosAgendamiento = recolectarDatosAgendamiento();
            if (datosAgendamiento.isEmpty()) {
                return;
            }

            // Procesar agendamiento a través del controlador
            controller.agendarCita(
                datosAgendamiento.get().getMascota(),
                datosAgendamiento.get().getVeterinario(),
                datosAgendamiento.get().getFecha(),
                datosAgendamiento.get().getHora(),
                datosAgendamiento.get().getMotivo()
            );
            
            // Actualizar UI después del éxito
            actualizarDespuesDeAgendamiento();
            
        } catch (Exception e) {
            App.showAlert("Error al agendar cita", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    void registrarMascotas(ActionEvent event) {
        try {
            // Recolectar datos del formulario
            String nombre = txtNombreMascota.getText();
            String especie = txtEspecie.getText();
            String raza = txtRaza.getText();
            String edad = txtEdad.getText();
            Propietario propietario = comboPropietarioMascota.getValue();
            
            // Registrar mascota a través del controlador
            controller.registrarMascota(nombre, especie, raza, edad, propietario);
            
            // Actualizar UI después del registro exitoso
            limpiarCamposMascota();
            cargarDatos();
            App.showAlert("Éxito", "Mascota registrada correctamente", AlertType.INFORMATION);
            
        } catch (Exception e) {
            App.showAlert("Error", e.getMessage(), AlertType.ERROR);
        }
    }
    
    private void limpiarCamposMascota() {
        txtNombreMascota.clear();
        txtEspecie.clear();
        txtRaza.clear();
        txtEdad.clear();
        comboPropietarioMascota.getSelectionModel().clearSelection();
    }

    @FXML
    void registrarPropietario(ActionEvent event) {
        try {
            // Recolectar datos del formulario
            String cedula = txtCedula.getText();
            String nombre = txtNombrePropietario.getText();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            String direccion = txtDireccion.getText();
            
            // Registrar propietario a través del controlador
            controller.registrarPropietario(cedula, nombre, telefono, email, direccion);
            
            // Actualizar UI después del registro exitoso
            limpiarCamposPropietario();
            cargarDatos();
            App.showAlert("Éxito", "Propietario registrado correctamente", AlertType.INFORMATION);
            
        } catch (Exception e) {
            App.showAlert("Error", e.getMessage(), AlertType.ERROR);
        }
    }
    
    private void limpiarCamposPropietario() {
        txtCedula.clear();
        txtNombrePropietario.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtDireccion.clear();
    }

    @FXML
    void registrarVeterinaria(ActionEvent event) {
        try {
            // Recolectar datos del formulario
            String cedula = txtCedulaVet.getText();
            String nombre = txtNombreVet.getText();
            String telefono = txtTelefonoVet.getText();
            String email = txtEmailVet.getText();
            Especialidad especialidad = comboEspecialidad.getValue();
            
            // Registrar veterinario a través del controlador
            controller.registrarVeterinario(cedula, nombre, especialidad, telefono, email);
            
            // Actualizar UI después del registro exitoso
            limpiarCamposVeterinario();
            cargarDatos();
            App.showAlert("Éxito", "Veterinario registrado correctamente", AlertType.INFORMATION);
            
        } catch (Exception e) {
            App.showAlert("Error", e.getMessage(), AlertType.ERROR);
        }
    }
    
    private void limpiarCamposVeterinario() {
        txtCedulaVet.clear();
        txtNombreVet.clear();
        txtTelefonoVet.clear();
        txtEmailVet.clear();
        comboEspecialidad.getSelectionModel().clearSelection();
    }

    @FXML
    void volverMenu(ActionEvent event) {
        App.loadScene("login", 640, 480);
    }

    // =====================================
    // Métodos auxiliares de agendamiento
    // =====================================

    private Optional<DatosAgendamiento> recolectarDatosAgendamiento() {
        // Obtener datos del formulario
        Mascota mascota = comboMascota.getValue();
        Veterinario veterinario = comboVeterinario.getValue();
        LocalDate fecha = date_Cita.getValue();
        String horaStr = comboHorario.getValue();
        String motivo = txtMotivo.getText();

        // Validar campos requeridos
        if (!validarCamposAgendamiento(mascota, veterinario, fecha, horaStr, motivo)) {
            return Optional.empty();
        }

        // Crear objeto con datos validados
        LocalTime hora = LocalTime.parse(horaStr);
        return Optional.of(new DatosAgendamiento(mascota, veterinario, fecha, hora, motivo));
    }

    private boolean validarCamposAgendamiento(
            Mascota mascota, Veterinario veterinario, 
            LocalDate fecha, String horaStr, String motivo) {
        if (mascota == null || veterinario == null || fecha == null || 
            horaStr == null || motivo.isEmpty()) {
            App.showAlert("Error de validación", "Todos los campos son requeridos", AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void actualizarDespuesDeAgendamiento() {
        cargarDatos();
        limpiarCampos();
        App.showAlert("Éxito", "Cita agendada correctamente", AlertType.INFORMATION);
    }

    private void limpiarCampos() {
        txtMotivo.clear();
        date_Cita.setValue(null);
        comboHorario.getSelectionModel().clearSelection();
        comboMascota.getSelectionModel().clearSelection();
        comboPropietario.getSelectionModel().clearSelection();
        comboPropietarioMascota.getSelectionModel().clearSelection();
        comboVeterinario.getSelectionModel().clearSelection();
    }

}
