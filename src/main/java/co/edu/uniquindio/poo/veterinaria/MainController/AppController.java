package co.edu.uniquindio.poo.veterinaria.MainController;

import java.io.IOException;

import co.edu.uniquindio.poo.veterinaria.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AppController {

    private static Scene scene;

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showAlertAndRedirect(String title, String message, AlertType type, String fxml,
            double width, double height) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setOnHidden(evt -> loadScene(fxml, width, height));
        alert.show();
    }

        public static void loadScene(String fxml, double width, double height) {
        try {
            // Normalizar el nombre del archivo (remover extensión .fxml si está presente)
            String normalizedFxml = fxml.replace(".fxml", "");

            // Registrar la acción para depuración
            System.out.println("Cargando escena: " + normalizedFxml);

            Parent root = loadFXML(normalizedFxml);
            if (scene != null) {
                scene.setRoot(root);
                scene.getWindow().setWidth(width);
                scene.getWindow().setHeight(height);
                System.out.println("Escena cargada exitosamente: " + normalizedFxml);
            } else {
                System.err.println("Error: Scene es null, no se puede cambiar la raíz");
                showAlert("Error al cambiar la vista",
                        "La escena principal no está inicializada correctamente.",
                        Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar FXML: " + fxml + " - " + e.getMessage());
            e.printStackTrace();
            showAlert("Error al cambiar la vista",
                    "No se pudo cargar el archivo FXML: " + fxml + "\n" + e.getMessage(),
                    Alert.AlertType.ERROR);
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar escena: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error inesperado",
                    "Ocurrió un error al cambiar la vista: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

}
