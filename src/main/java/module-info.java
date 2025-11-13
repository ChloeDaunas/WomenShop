module com.example.womenshop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.womenshop to javafx.fxml;
    exports com.example.womenshop;
}