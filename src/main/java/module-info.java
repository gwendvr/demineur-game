module com.demineur {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.demineur to javafx.fxml;
    exports com.demineur;
}