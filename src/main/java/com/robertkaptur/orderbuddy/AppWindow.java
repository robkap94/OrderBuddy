package com.robertkaptur.orderbuddy;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.*;

import java.io.IOException;

public class AppWindow {

    private Stage windowStage;

    public AppWindow(Stage windowStage, String fxmlLoader, int width, int height, double positionOnScreen, String title,
                     boolean isGroupedWindow, boolean isShowAndWait) throws IOException {
        // isGroupedWindow boolean is to confirm whether this window is grouped and if it will be closed than rest of (grouped) windows would be close as well
        // above functionality should be utilized only for main windows - manager & queue views

        // isShowAndWait boolean confirms whether this window, after appearing, is blocking (from action) other windows [true] or not [false]
        // above functionality is mostly suggested for small windows i.e. "category manager"

        this.windowStage = windowStage;

        // Creating FXMLLoader & Scene
        FXMLLoader windowFxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/" + fxmlLoader));
        Scene sceneView = new Scene(windowFxmlLoader.load(), width, height);
        // Setting up parameters
        windowStage.setTitle(title);
        windowStage.setScene(sceneView);
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
        // Before showing window, checking whether it should appear and block other windows
        if(isShowAndWait) {
            windowStage.initModality(Modality.APPLICATION_MODAL);
        }
        // Showing the window
        windowStage.show();
        // Positioning Manager View's window
        Rectangle2D windowViewBounds = Screen.getPrimary().getVisualBounds();
        double windowPosX = windowViewBounds.getMinX() + (windowViewBounds.getWidth() - sceneView.getWidth()) * positionOnScreen;
        windowStage.setX(windowPosX);
        if(isGroupedWindow) {
            // Auto-closure, of whole app, when window is closed. This will close other window too.
            windowStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    showExitConfirmationAlert();
                    windowEvent.consume();
                }
            });
        }

    }

    public static void showExitConfirmationAlert() { // Opens additional confirmation dialog when clicked window's exit icon (X)
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to exit?", ButtonType.YES, ButtonType.NO);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
    }

    public void resizableWindow(boolean isResizable) {
        if(isResizable) {
            windowStage.setResizable(true);
        } else {
            windowStage.setResizable(false);
        }
    }
}
