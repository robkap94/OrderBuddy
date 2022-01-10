package com.robertkaptur.orderbuddy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene managerView = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Order Buddy - Manager View");
        stage.setScene(managerView);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}