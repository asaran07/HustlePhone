module com.example.vom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;


    exports com.example.vom;
    opens com.example.vom to javafx.fxml;
}