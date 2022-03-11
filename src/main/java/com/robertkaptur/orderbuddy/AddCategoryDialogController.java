package com.robertkaptur.orderbuddy;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddCategoryDialogController {

    // FXML Field
    @FXML
    private TextField nameTextField;

    public Category processCategory() { // Processing category in window dialog
        String name = nameTextField.getText().trim();

        return new Category(name);
    }
}