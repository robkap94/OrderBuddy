package com.robertkaptur.orderbuddy;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditCategoryDialogController {

    // FXML Field
    @FXML
    private TextField oldNameTextField;
    @FXML
    private TextField newNameTextField;

    @FXML
    public void initialize() {

    }

    public String processNewCategoryName() { // Processing category's new name in window dialog
        String name = newNameTextField.getText().trim();

        return name;
    }

    public void processOldName(Category category) {
        oldNameTextField.setText(category.getCategoryName());
    }
}