module co.edu.uniquindio.poo.veterinaria {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens co.edu.uniquindio.poo.veterinaria to javafx.fxml;
    exports co.edu.uniquindio.poo.veterinaria;

    opens co.edu.uniquindio.poo.veterinaria.viewController to javafx.fxml;
    exports co.edu.uniquindio.poo.veterinaria.viewController;
    
    opens co.edu.uniquindio.poo.veterinaria.controller to javafx.fxml;
    exports co.edu.uniquindio.poo.veterinaria.controller;

    opens co.edu.uniquindio.poo.veterinaria.model to javafx.fxml;
    exports co.edu.uniquindio.poo.veterinaria.model;

    
}
