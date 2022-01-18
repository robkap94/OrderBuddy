package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class ManagerController {
    // FXML Fields
    @FXML
    ListView<Order> ordersListView = new ListView<>();
    @FXML
    BorderPane managerBorderPane;

    // Fields
//    ObservableList<Order> ordersList = FXCollections.observableList(); //TODO: Populate it by observableList and uncomment

    @FXML
    public void initialize() {
        ordersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ordersListView.getSelectionModel().selectFirst();
        ordersListView.setItems(FXCollections.observableArrayList());
    }

    @FXML
    protected void onCreateButtonClicked() {
        showCreateOrderDialog();
    }

    @FXML
    public void showCreateOrderDialog() {
        Dialog<ButtonType> dialogWindow = new Dialog<>();
        dialogWindow.initOwner(managerBorderPane.getScene().getWindow());
        dialogWindow.setHeaderText("This is create Order dialog");
        dialogWindow.setTitle("Create new order");
        dialogWindow.setContentText("This is simple dialog");

        FXMLLoader dialogLoader = new FXMLLoader();
        dialogLoader.setLocation(MainApplication.class.getResource("fxml/addOrderDialog-view.fxml"));
        try {
            dialogWindow.getDialogPane().setContent(dialogLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog, error during loading FXML of Dialog");
            e.printStackTrace();
        }

        dialogWindow.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialogWindow.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialogWindow.showAndWait();

        if((result.isPresent()) && (result.get() == ButtonType.OK)) {
            AddOrderDialogController controller = dialogLoader.getController();
            Order newOrder = controller.processOrder();
            // TODO: Add here newOrder into ObservableList
            ordersListView.getSelectionModel().select(newOrder);
        }
    }
}