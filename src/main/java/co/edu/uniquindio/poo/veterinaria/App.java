package co.edu.uniquindio.poo.veterinaria;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import co.edu.uniquindio.poo.veterinaria.MainController.AppController;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Inicializar la escena principal usando AppController
        Parent root = AppController.loadFXML("login");
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
        
    }

    public static void main(String[] args) {
        launch();
    }

}