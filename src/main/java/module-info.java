module com.example.vom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    exports com.example.vom;
    opens com.example.vom to javafx.fxml;
}