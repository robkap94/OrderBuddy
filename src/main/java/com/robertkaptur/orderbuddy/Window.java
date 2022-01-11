package com.robertkaptur.orderbuddy;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Window {

    public Window(Stage windowStage, String fxmlLoader, int width, int height, double positionOnScreen, String title) throws IOException {

        // Loading of the Manager View's window
        FXMLLoader windowFxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/" + fxmlLoader));
        Scene managerView = new Scene(windowFxmlLoader.load(), width, height);
        windowStage.setTitle(title);
        windowStage.setScene(managerView);
        windowStage.getIcons().add(new Image((String.valueOf(MainApplication.class.getResource("img/OrderBuddy.png")))));
        windowStage.show();
        // Positioning Manager View's window
        Rectangle2D windowViewBounds = Screen.getPrimary().getVisualBounds();
        double windowPosX = windowViewBounds.getMinX() + (windowViewBounds.getWidth() - managerView.getWidth()) * positionOnScreen;
        windowStage.setX(windowPosX);
    }
}
