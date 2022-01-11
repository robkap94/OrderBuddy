package com.robertkaptur.orderbuddy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage managerStage) throws IOException {
        // Loading of the Manager View's window
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("manager-view.fxml"));
        Scene managerView = new Scene(fxmlLoader.load(), 800, 600);
        managerStage.setTitle("Order Buddy - Manager View");
        managerStage.setScene(managerView);
        managerStage.show();
        // Positioning Manager View's window
        Rectangle2D managerViewBounds = Screen.getPrimary().getVisualBounds();
        double managerPosX = managerViewBounds.getMinX() + (managerViewBounds.getWidth() - managerView.getWidth()) * 0.1;
        managerStage.setX(managerPosX);
    }

    public static void main(String[] args) {
        launch();
    }
}