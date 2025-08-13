module co.edu.uniquindio.poo.veterinaria {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens co.edu.uniquindio.poo.veterinaria to javafx.fxml;
    exports co.edu.uniquindio.poo.veterinaria;
}
