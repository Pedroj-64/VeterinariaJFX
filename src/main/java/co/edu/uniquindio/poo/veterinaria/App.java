package co.edu.uniquindio.poo.veterinaria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Carga y establece una nueva escena en la ventana principal.
     * 
     * @param fxml   El nombre del archivo FXML.
     * @param width  El ancho de la nueva escena.
     * @param height La altura de la nueva escena.
     */
    public static void loadScene(String fxml, double width, double height) {
        try {
            Parent root = loadFXML(fxml);
            scene.setRoot(root);
            scene.getWindow().setWidth(width);
            scene.getWindow().setHeight(height);

            // Agregar la escena al historial

        } catch (IOException e) {
            showAlert("Error al cambiar la vista", "No se pudo cargar el archivo FXML: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Muestra una alerta con el mensaje especificado.
     * 
     * @param title   El título de la alerta.
     * @param message El contenido del mensaje.
     * @param type    El tipo de alerta.
     */
    public static void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type); // Crear la alerta
        alert.setTitle(title); // Establecer el título de la alerta
        alert.setHeaderText(null); // No usar encabezado
        alert.setContentText(message); // Establecer el contenido del mensaje
        alert.showAndWait(); // Mostrar la alerta y esperar a que el usuario la cierre
    }

    /**
     * Muestra una alerta y redirige a una nueva escena al cerrar la alerta.
     * 
     * @param title   El título de la alerta.
     * @param message El contenido del mensaje.
     * @param type    El tipo de alerta.
     * @param fxml    El nombre del archivo FXML de la nueva escena.
     * @param width   El ancho de la nueva escena.
     * @param height  La altura de la nueva escena.
     */
    public static void showAlertAndRedirect(String title, String message, AlertType type, String fxml, double width,
            double height) {
        Alert alert = new Alert(type); // Crear la alerta
        alert.setTitle(title); // Establecer el título
        alert.setHeaderText(null); // Sin encabezado
        alert.setContentText(message); // Mensaje

        // Redirigir a una nueva escena al cerrar la alerta
        alert.setOnHidden(evt -> loadScene(fxml, width, height));
        alert.show(); // Mostrar la alertan
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Obtiene la instancia única de la veterinaria.
     * 
     * @return La instancia de Veterinaria
     */
    public static Veterinaria getVeterinaria() {
        return Veterinaria.getInstance();
    }

}