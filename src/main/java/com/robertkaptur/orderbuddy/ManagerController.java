package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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
    ObservableList<Order> ordersList = FXCollections.observableArrayList();
    OrderData orderData = OrderData.getInstance();

    @FXML
    public void initialize() {
        ordersList.setAll(orderData.getListOfOrders()); // observableArrayList in ManagerControl, is populated by observableArrayList from OrderData

        ordersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Setting selection model
        ordersListView.getSelectionModel().selectFirst(); // Will be selected first, existing, order
        ordersListView.setItems(ordersList); // Setting ListView for items from ordersList (observableArrayList)
        updateListViewCellFactory(); // Setting up proper way of updating cells in ListView's CellFactory
    }

    @FXML
    protected void onCreateButtonClicked() {
        showCreateOrderDialog();
    }

    @FXML
    protected void onEditButtonClicked() {
    }

    @FXML
    public void showCreateOrderDialog() {

        // Initializing, declaring and processing Dialog Window to create Order

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
            e.printStackTrace();
        }

        dialogWindow.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialogWindow.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialogWindow.showAndWait();

        // Processing results of dialog window

        if((result.isPresent()) && (result.get() == ButtonType.OK)) {
            AddOrderDialogController controller = dialogLoader.getController();
            Order newOrder = controller.processOrder();
            ordersList.add(newOrder); // Adding to FXCollections' observableArrayList
            ordersListView.setItems(ordersList); // Setting ListView to items which are in observableArrayList
            ordersListView.getSelectionModel().select(newOrder); // Selecting, on ListView, newly created order
            orderData.addOrder(newOrder); // Adding, newly created order, into OrderData instance

            //Saving database after file creation

            try {
                orderData.saveDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateListViewCellFactory() {
        ordersListView.setCellFactory(param -> new ListCell<>() { // Utilizing Cell Factory to ensure ListView is not named by object's hash but id + title
            @Override
            protected void updateItem(Order order, boolean b) {
                super.updateItem(order, b);

                if(b || order == null || order.getTitle() == null) {
                    setText(null);
                } else {
                    setText("id " + order.getId() + ": " + order.getTitle());
                }
            }
        });
    }
}