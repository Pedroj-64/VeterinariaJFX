package co.edu.uniquindio.poo.veterinaria.viewController;

import java.time.LocalDate;

import co.edu.uniquindio.poo.veterinaria.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private TableColumn<LocalDate, ? > tbc_fecha;

    @FXML
    private TableColumn<?, ?> tbc_hora;

    @FXML
    private TableColumn<?, ?> tbc_nombre;

    @FXML
    private TableColumn<?, ?> tbc_nombrePropietarios;

    @FXML
    private TableColumn<?, ?> tbc_nombreVeterinario;

    @FXML
    private TableColumn<?, ?> tbc_propietario;

    @FXML
    private TableColumn<?, ?> tbc_raza;

    @FXML
    private TableColumn<?, ?> tbc_telefono;

    @FXML
    private TableColumn<?, ?> tbc_telefonoVeterinario;

    @FXML
    private TableColumn<?, ?> tbc_veterinario;

    @FXML
    private TableView<?> tbl_citas;

    @FXML
    private TableView<?> tbl_mascota;

    @FXML
    private TableView<?> tbl_propietarios;

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

    @FXML
    private TextField txtNombreVet;

    @FXML
    private TextField txtRaza;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtTelefonoVet;

    @FXML
    void agendarCita(ActionEvent event) {

    }

    @FXML
    void registrarMascotas(ActionEvent event) {

    }

    @FXML
    void registrarPropietario(ActionEvent event) {

    }

    @FXML
    void registrarVeterinaria(ActionEvent event) {

    }

    @FXML
    void volverMenu(ActionEvent event) {

    }

}
