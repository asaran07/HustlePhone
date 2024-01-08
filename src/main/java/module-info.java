module com.example.vom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.vom to javafx.fxml;
    exports com.example.vom;
}