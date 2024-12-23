module com.emaple.preparationalexamjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.emaple.preparationalexamjava to javafx.fxml;
    exports com.emaple.preparationalexamjava;
}