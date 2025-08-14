package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginViewController {

    @FXML
    private Button btn_Inicio;

    @FXML
    private ImageView img_logo;

    @FXML
    private Label lbl_bienvenidos;

    @FXML
    private Label lbl_iniciarSesion;

    @FXML
    private PasswordField txt_contraseña;

    @FXML
    private TextField txt_usuario;

    @FXML
    void iniciarSesion(ActionEvent event) {
        String usuario = txt_usuario.getText();
        String contraseña = txt_contraseña.getText();

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            App.showAlert("Error", "Los campos no pueden estar vacíos", AlertType.ERROR);
            return;
        }

        if (LoginController.validarCredenciales(usuario, contraseña)) {
            if (LoginController.esAdmin(usuario, contraseña)) {
                App.loadScene("superusuario", 800, 600);
            } else {
                App.loadScene("menuVeterinario", 800, 600);
            }
        } else {
            App.showAlert("Error", "Credenciales inválidas", AlertType.ERROR);
        }
    }
}
