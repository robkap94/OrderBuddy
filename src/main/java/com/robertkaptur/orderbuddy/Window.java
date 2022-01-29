package com.robertkaptur.orderbuddy;

import javafx.application.Platform;
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
        // Creating FXMLLoader & Scene
        FXMLLoader windowFxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/" + fxmlLoader));
        Scene managerView = new Scene(windowFxmlLoader.load(), width, height);
        // Setting up parameters
        windowStage.setTitle(title);
        windowStage.setScene(managerView);
        windowStage.getIcons().add(new Image((String.valueOf(MainApplication.class.getResource("img/OrderBuddy.png")))));
        // Minimal size of window
        /*
        This is border size, to be utilized in min height/width of the window
        INFO for Future Robert: I have used this, because SceneBuilder has some kind of weird border and without addition
        of borderSize to width/height, it's not looking good in
        */
        int borderSize = 30;
        windowStage.setMinHeight(height + borderSize);
        windowStage.setMinWidth(width + borderSize);
        // Showing the window
        windowStage.show();
        // Positioning Manager View's window
        Rectangle2D windowViewBounds = Screen.getPrimary().getVisualBounds();
        double windowPosX = windowViewBounds.getMinX() + (windowViewBounds.getWidth() - managerView.getWidth()) * positionOnScreen;
        windowStage.setX(windowPosX);
        windowStage.setOnCloseRequest(windowEvent -> Platform.exit()); // Auto-closure, of whole app, when window is closed. This will close other window too.
    }
}
