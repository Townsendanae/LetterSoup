module com.grupo3.proyectoprimerparcial {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.base;

    opens com.grupo3.proyectoprimerparcial to javafx.fxml;
    exports com.grupo3.proyectoprimerparcial;
}
