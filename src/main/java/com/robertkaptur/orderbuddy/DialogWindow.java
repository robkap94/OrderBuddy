package com.robertkaptur.orderbuddy;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

public class DialogWindow {

    private Dialog<ButtonType> dialog;
    private final Window initOwnerWindow;
    private final String headerText;
    private final String titleText;
    private final String fxmlResource;
    private FXMLLoader dialogLoader;

    public DialogWindow(Window initOwnerWindow, String headerText, String titleText, String fxmlResource) {
        this.initOwnerWindow = initOwnerWindow;
        this.headerText = headerText;
        this.titleText = titleText;
        this.fxmlResource = fxmlResource;
    }

    public void prepareDialog(boolean hasOkButton, boolean hasCancelButton) {
        dialog = new Dialog<>();
        dialog.initOwner(initOwnerWindow);
        dialog.setHeaderText(headerText);
        dialog.setTitle(titleText);

        dialogLoader = new FXMLLoader();
        dialogLoader.setLocation(MainApplication.class.getResource(fxmlResource));
        try {
            dialog.getDialogPane().setContent(dialogLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(hasOkButton) {
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        }
        if(hasCancelButton) {
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        }
    }

    public Optional<ButtonType> launchDialogResult() {
        return dialog.showAndWait();
    }

    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    public FXMLLoader getDialogLoader() {
        return dialogLoader;
    }
}
