module com.robertkaptur.orderbuddy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.robertkaptur.orderbuddy to javafx.fxml;
    exports com.robertkaptur.orderbuddy;
}