module com.example.womenshop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.base;
    requires java.sql;
    //requires com.example.womenshop;

    opens com.example.womenshop to javafx.fxml;
    exports com.example.womenshop;
    exports com.example.womenshop.app;
    opens com.example.womenshop.app to javafx.fxml;
    exports com.example.womenshop.Controller;
    opens com.example.womenshop.Controller to javafx.fxml;
    exports com.example.womenshop.model;
    opens com.example.womenshop.model to javafx.fxml;
    exports com.example.womenshop.util;
    opens com.example.womenshop.util to javafx.fxml;
}