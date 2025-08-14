package co.edu.uniquindio.poo.veterinaria.viewController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ListCell;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.model.Especialidad;
import co.edu.uniquindio.poo.veterinaria.model.Mascota;
import co.edu.uniquindio.poo.veterinaria.model.Veterinario;
import co.edu.uniquindio.poo.veterinaria.model.Propietario;
import co.edu.uniquindio.poo.veterinaria.model.Cita;
import co.edu.uniquindio.poo.veterinaria.util.DatosAgendamiento;
import javafx.scene.control.Alert.AlertType;
import co.edu.uniquindio.poo.veterinaria.controller.SuperUsuarioController;
import javafx.collections.FXCollections;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableColumn<Veterinario, String> tbc_Especialidad;

    @FXML
    private TableColumn<Mascota, String> tbc_Mascota;

    @FXML
    private TableColumn<Propietario, String> tbc_cedula;

    @FXML
    private TableColumn<Veterinario, String> tbc_cedulaVeterinario;

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
    private TableView<Veterinario> tbl_veterinarios;

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
        Optional.ofNullable(comboHorario)
            .ifPresent(c -> configurarComboSimple(c, "Seleccione un horario"));

        Optional.ofNullable(comboEspecialidad)
            .ifPresent(c -> configurarComboSimple(c, "Seleccione una especialidad", 
                esp -> esp.toString().replace("_", " ")));

        Optional.ofNullable(comboMascota)
            .ifPresent(c -> configurarComboSimple(c, "Seleccione una mascota", 
                Mascota::getNombre));

        Function<Propietario, String> formatoPropietario = 
            prop -> prop.getNombre() + " - " + prop.getCedula();

        Stream.of(comboPropietario, comboPropietarioMascota)
            .filter(Objects::nonNull)
            .forEach(c -> configurarComboSimple(c, "Seleccione un propietario", 
                formatoPropietario));

        Optional.ofNullable(comboVeterinario)
            .ifPresent(c -> configurarComboSimple(c, "Seleccione un veterinario",
                vet -> vet.getNombre() + " - " + vet.getEspecialidad()));
    }

    private <T> void configurarComboSimple(ComboBox<T> combo, String prompt) {
        configurarComboSimple(combo, prompt, Objects::toString);
    }

    private <T> void configurarComboSimple(ComboBox<T> combo, String prompt, Function<T, String> formatter) {
        combo.setPromptText(prompt);
        var cell = new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.apply(item));
            }
        };
        combo.setCellFactory(view -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.apply(item));
            }
        });
        combo.setButtonCell(cell);
    }



    private void configurarListeners() {
        // Listeners para actualizar horarios cuando cambie el veterinario o la fecha
        if (comboVeterinario != null) {
            comboVeterinario.valueProperty().addListener((obs, oldVal, newVal) -> {
                // Limpiar fecha si se cambia de veterinario
                if (oldVal != null && newVal != null && !oldVal.equals(newVal)) {
                    if (date_Cita != null) {
                        date_Cita.setValue(null);
                    }
                }
                actualizarHorarios();
            });
        }

        if (date_Cita != null) {
            date_Cita.valueProperty().addListener((obs, oldVal, newVal) -> {
                // Si se selecciona una fecha pero no hay veterinario
                if (newVal != null && comboVeterinario.getValue() == null) {
                    App.showAlert("Aviso", "Por favor, seleccione primero un veterinario", AlertType.WARNING);
                    date_Cita.setValue(null);
                    return;
                }
                actualizarHorarios();
            });
        }

        // Listener para filtrar mascotas cuando cambie el propietario
        if (comboPropietarioMascota != null) {
            comboPropietarioMascota.valueProperty().addListener((obs, oldVal, newVal) -> {
                actualizarMascotasPorPropietario();
            });
        }
    }

    private void configurarTablas() {
        try {
            configurarTablaPropietarios();
            configurarTablaVeterinarios();
            configurarTablaMascotas();
            configurarTablaCitas();
        } catch (Exception e) {
            App.showAlert("Error", "Error al configurar las tablas: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private <S, T> void configurarColumnaTexto(TableColumn<S, String> columna, Function<S, T> extractor) {
        if (columna == null) return;
        columna.setCellValueFactory(data -> 
            Optional.ofNullable(data.getValue())
                .map(extractor)
                .map(Objects::toString)
                .map(SimpleStringProperty::new)
                .orElse(new SimpleStringProperty("")));
    }

    private void configurarTablaPropietarios() {
        if (tbl_propietarios == null) return;

        configurarColumnaTexto(tbc_cedula, Propietario::getCedula);
        configurarColumnaTexto(tbc_nombrePropietarios, Propietario::getNombre);
        configurarColumnaTexto(tbc_telefono, Propietario::getTelefono);
        configurarColumnaTexto(tbc_email, Propietario::getEmail);
        configurarColumnaTexto(tbc_propietario, Propietario::getDireccion);
    }

    private void configurarTablaVeterinarios() {
        if (tbl_veterinarios == null) return;

        configurarColumnaTexto(tbc_cedulaVeterinario, Veterinario::getCedula);
        configurarColumnaTexto(tbc_nombreVeterinario, Veterinario::getNombre);
        configurarColumnaTexto(tbc_telefonoVeterinario, Veterinario::getTelefono);
        configurarColumnaTexto(tbc_Especialidad, vet -> 
            Optional.ofNullable(vet.getEspecialidad())
                .map(esp -> esp.toString().replace("_", " "))
                .orElse(""));
    }

    private void configurarTablaMascotas() {
        if (tbl_mascota == null) return;

        configurarColumnaTexto(tbc_Mascota, Mascota::getNombre);
        configurarColumnaTexto(tbc_especie, Mascota::getEspecie);
        configurarColumnaTexto(tbc_raza, Mascota::getRaza);
    }

    private void configurarTablaCitas() {
        if (tbl_citas == null) return;

        tbc_veterinario.setCellValueFactory(celda -> 
            Optional.ofNullable(celda.getValue())
                .map(Cita::getVeterinario)
                .map(SimpleObjectProperty::new)
                .orElse(new SimpleObjectProperty<>()));
                
        tbc_veterinario.setCellFactory(col -> new TableCell<Cita, Veterinario>() {
            @Override
            protected void updateItem(Veterinario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });

        tbc_fecha.setCellValueFactory(celda -> 
            Optional.ofNullable(celda.getValue())
                .map(Cita::getFechaHora)
                .map(LocalDateTime::toLocalDate)
                .map(SimpleObjectProperty::new)
                .orElse(new SimpleObjectProperty<>()));

        tbc_hora.setCellValueFactory(celda -> 
            Optional.ofNullable(celda.getValue())
                .map(Cita::getFechaHora)
                .map(LocalDateTime::toLocalTime)
                .map(LocalTime::toString)
                .map(SimpleStringProperty::new)
                .orElse(new SimpleStringProperty("")));
    }



    private void cargarDatos() {
        try {          limpiarTablasYCombos();
            

            var propietarios = controller.obtenerPropietarios();
            var mascotas = controller.obtenerMascotas();
            var citas = controller.obtenerCitas();
            var veterinarios = controller.obtenerVeterinarios();
            var especialidades = controller.obtenerEspecialidades();
            System.out.println("Propietarios cargados: " + (propietarios != null ? propietarios.size() : 0));
            System.out.println("Mascotas cargadas: " + (mascotas != null ? mascotas.size() : 0));
            System.out.println("Veterinarios cargados: " + (veterinarios != null ? veterinarios.size() : 0));
            
            var listaPropietarios = FXCollections.observableArrayList(
                propietarios != null ? propietarios : new java.util.ArrayList<>());
            var listaMascotas = FXCollections.observableArrayList(
                mascotas != null ? mascotas : new java.util.ArrayList<>());
            var listaCitas = FXCollections.observableArrayList(
                citas != null ? citas : new java.util.ArrayList<>());
            var listaVeterinarios = FXCollections.observableArrayList(
                veterinarios != null ? veterinarios : new java.util.ArrayList<>());
            var listaEspecialidades = FXCollections.observableArrayList(
                especialidades != null ? especialidades : new java.util.ArrayList<>());

            // Cargar datos en las tablas
            if (tbl_propietarios != null) {
                tbl_propietarios.setItems(null); // Limpiar primero
                tbl_propietarios.setItems(listaPropietarios);
            }
            
            if (tbl_mascota != null) {
                tbl_mascota.setItems(null); // Limpiar primero
                tbl_mascota.setItems(listaMascotas);
            }
            
            if (tbl_citas != null) {
                tbl_citas.setItems(null); // Limpiar primero
                tbl_citas.setItems(listaCitas);
            }

            if (tbl_veterinarios != null) {
                tbl_veterinarios.setItems(null); // Limpiar primero
                tbl_veterinarios.setItems(listaVeterinarios);
            }

            // Cargar datos en los ComboBox
            if (comboPropietario != null) {
                comboPropietario.setItems(null); // Limpiar primero
                comboPropietario.setItems(listaPropietarios);
            }
            
            if (comboPropietarioMascota != null) {
                comboPropietarioMascota.setItems(null); // Limpiar primero
                comboPropietarioMascota.setItems(listaPropietarios);
            }
            
            if (comboVeterinario != null) {
                comboVeterinario.setItems(null); // Limpiar primero
                comboVeterinario.setItems(listaVeterinarios);
            }
            
            if (comboEspecialidad != null) {
                comboEspecialidad.setItems(null); // Limpiar primero
                comboEspecialidad.setItems(listaEspecialidades);
            }

            // Limpiar selecciones
            if (comboHorario != null) comboHorario.getSelectionModel().clearSelection();
            if (comboMascota != null) comboMascota.getSelectionModel().clearSelection();
            if (comboPropietario != null) comboPropietario.getSelectionModel().clearSelection();
            if (comboPropietarioMascota != null) comboPropietarioMascota.getSelectionModel().clearSelection();
            if (comboVeterinario != null) comboVeterinario.getSelectionModel().clearSelection();
            if (comboEspecialidad != null) comboEspecialidad.getSelectionModel().clearSelection();

        } catch (Exception e) {
            e.printStackTrace(); // Para debugging
            App.showAlert("Error", "Error al cargar los datos: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void actualizarHorarios() {
        if (comboHorario == null) return;
        
        comboHorario.getItems().clear();
        comboHorario.setPromptText("Seleccione un horario");

        Veterinario veterinario = comboVeterinario.getValue();
        LocalDate fecha = date_Cita.getValue();

        if (veterinario == null) {
            comboHorario.setPromptText("Primero seleccione un veterinario");
            return;
        }

        if (fecha == null) {
            comboHorario.setPromptText("Seleccione una fecha");
            return;
        }

        // Validar que la fecha no sea anterior a hoy
        if (fecha.isBefore(LocalDate.now())) {
            App.showAlert("Error", "No se pueden agendar citas en fechas pasadas", AlertType.ERROR);
            date_Cita.setValue(null);
            return;
        }

        try {
            var horarios = veterinario.getHorariosDisponibles(fecha);
            if (horarios.isEmpty()) {
                comboHorario.setPromptText("No hay horarios disponibles para esta fecha");
            } else {
                comboHorario.setPromptText("Seleccione un horario");
                comboHorario.setItems(FXCollections.observableArrayList(
                        horarios.stream()
                                .map(LocalTime::toString)
                                .collect(Collectors.toList())));
            }
        } catch (Exception e) {
            App.showAlert("Error", "Error al cargar los horarios: " + e.getMessage(), AlertType.ERROR);
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
                    datosAgendamiento.get().getMotivo());

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
            String nombre = txtNombreMascota.getText().trim();
            String especie = txtEspecie.getText().trim();
            String raza = txtRaza.getText().trim();
            String edad = txtEdad.getText().trim();
            Propietario propietario = comboPropietarioMascota.getValue();

            // Validar campos requeridos
            if (nombre.isEmpty() || especie.isEmpty() || raza.isEmpty() || edad.isEmpty() || propietario == null) {
                App.showAlert("Error", "Todos los campos son requeridos", AlertType.ERROR);
                return;
            }

            // Validar que la edad sea un número
            try {
                Integer.parseInt(edad);
            } catch (NumberFormatException e) {
                App.showAlert("Error", "La edad debe ser un número", AlertType.ERROR);
                return;
            }

        
            controller.registrarMascota(nombre, especie, raza, edad, propietario);

    
            limpiarCamposMascota();
            cargarDatos(); 
            Platform.runLater(() -> {
                cargarDatos(); 
                App.showAlert("Éxito", "Mascota registrada correctamente", AlertType.INFORMATION);
            });

        } catch (Exception e) {
            App.showAlert("Error", "Error al registrar la mascota: " + e.getMessage(), AlertType.ERROR);
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
         
            String cedula = txtCedula.getText();
            String nombre = txtNombrePropietario.getText();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            String direccion = txtDireccion.getText();

            controller.registrarPropietario(cedula, nombre, telefono, email, direccion);

            
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
        App.loadScene("login", 800, 600);
    }


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

    private void limpiarTablasYCombos() {
        // Limpiar tablas
        if (tbl_propietarios != null) tbl_propietarios.getItems().clear();
        if (tbl_mascota != null) tbl_mascota.getItems().clear();
        if (tbl_citas != null) tbl_citas.getItems().clear();

        // Limpiar combos
        if (comboHorario != null) {
            comboHorario.getItems().clear();
            comboHorario.setPromptText("Seleccione un horario");
        }
        if (comboMascota != null) {
            comboMascota.getItems().clear();
            comboMascota.setPromptText("Seleccione una mascota");
        }
        if (comboPropietario != null) {
            comboPropietario.getItems().clear();
            comboPropietario.setPromptText("Seleccione un propietario");
        }
        if (comboPropietarioMascota != null) {
            comboPropietarioMascota.getItems().clear();
            comboPropietarioMascota.setPromptText("Seleccione un propietario");
        }
        if (comboVeterinario != null) {
            comboVeterinario.getItems().clear();
            comboVeterinario.setPromptText("Seleccione un veterinario");
        }
        if (comboEspecialidad != null) {
            comboEspecialidad.getItems().clear();
            comboEspecialidad.setPromptText("Seleccione una especialidad");
        }
    }

}
