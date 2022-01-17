package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ManagerController {
    @FXML
    ListView<String> ordersListView = new ListView<>();
    ObservableList<String> titlesList = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3");

    @FXML
    protected void onCreateButtonClicked() {
        ordersListView.setItems(titlesList);
    }
}