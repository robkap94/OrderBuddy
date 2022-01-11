package com.robertkaptur.orderbuddy;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Window {

    public Window(Stage windowStage, String fxmlLoader, int width, int height, double positionOnScreen) throws IOException {

        // Loading of the Manager View's window
        FXMLLoader windowFxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlLoader));
        Scene managerView = new Scene(windowFxmlLoader.load(), width, height);
        windowStage.setTitle("Order Buddy - Manager View");
        windowStage.setScene(managerView);
        windowStage.show();
        // Positioning Manager View's window
        Rectangle2D windowViewBounds = Screen.getPrimary().getVisualBounds();
        double windowPosX = windowViewBounds.getMinX() + (windowViewBounds.getWidth() - managerView.getWidth()) * positionOnScreen;
        windowStage.setX(windowPosX);
    }
}
