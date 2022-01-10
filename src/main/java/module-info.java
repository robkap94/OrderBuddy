module com.robertkaptur.orderbuddy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.robertkaptur.orderbuddy to javafx.fxml;
    exports com.robertkaptur.orderbuddy;
}