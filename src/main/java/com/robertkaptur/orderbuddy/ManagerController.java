package com.robertkaptur.orderbuddy;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ManagerController {

    // FXML Fields
    @FXML
    private ListView<Order> ordersListView = new ListView<>();
    @FXML
    private BorderPane managerBorderPane;
    @FXML
    private MenuItem createOrderMenuItem;
    @FXML
    private MenuItem deleteOrderMenuItem;
    @FXML
    private MenuItem editOrderMenuItem;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem categoryManagerMenuItem;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button showInQueueButton;
    @FXML
    private TextField titleOrderDetailField;
    @FXML
    private TextField idOrderDetailField;
    @FXML
    private TextField categoryOrderDetailField;
    @FXML
    private TextField priceOrderDetailField;
    @FXML
    private TextArea descriptionOrderDetailField;
    @FXML
    private DatePicker dooOrderDetailField;
    @FXML
    private DatePicker dodOrderDetailField;


    // Fields
    private ObservableList<Order> ordersList = FXCollections.observableArrayList();
    private OrderData orderData = OrderData.getInstance();
    private Order currentlySelectedOrder;

    @FXML
    public void initialize() { // During init of ManagerController
        ordersList.setAll(orderData.getListOfOrders()); // observableArrayList in ManagerControl, is populated by observableArrayList from OrderData

        ordersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Setting selection model
        ordersListView.getSelectionModel().selectFirst(); // Will be selected first, existing, order
        ordersListView.setItems(ordersList); // Setting ListView for items from ordersList (observableArrayList)
        updateListViewCellFactory(); // Setting up proper way of updating cells in ListView's CellFactory

        // Disabling buttons on init (will be handled, during app's lifecycle, by event listener

        deleteOrderMenuItem.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        showInQueueButton.setDisable(true);
        editOrderMenuItem.setDisable(true);

        // Listener for selected order

        ordersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observableValue, Order oldValue, Order newValue) {
                if(newValue == null) {
                    deleteOrderMenuItem.setDisable(true);
                    deleteButton.setDisable(true);
                    editButton.setDisable(true);
                    showInQueueButton.setDisable(true);
                    editOrderMenuItem.setDisable(true);
                } else {
                    deleteOrderMenuItem.setDisable(false);
                    deleteButton.setDisable(false);
                    editButton.setDisable(false);
                    showInQueueButton.setDisable(false);
                    editOrderMenuItem.setDisable(false);
                    currentlySelectedOrder = newValue;
                    showOrderDetailsInPane(currentlySelectedOrder);
                }
            }
        });
    }

    @FXML
    protected void onCreateButtonClicked() {
        showCreateOrderDialog();
    }

    @FXML
    protected void onEditButtonClicked() {
        showEditOrderDialog();
    }

    @FXML
    protected void onDeleteButtonClicked() {
        if(currentlySelectedOrder != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to delete order 'id " + currentlySelectedOrder.getId()
                    + ": " + currentlySelectedOrder.getTitle() + "'?", ButtonType.YES, ButtonType.NO);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {

                // Deleting order from SQL DB

                try {
                    orderData.deleteOrderFromSqlDatabase(currentlySelectedOrder);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                orderData.deleteOrder(currentlySelectedOrder); // Removing, selected order, from OrderData instance
                ordersList.remove(currentlySelectedOrder); // Removing from FXCollections' observableArrayList
            }
        }
    }

    @FXML
    protected void onCloseMenuItemClicked() { // Opens additional confirmation dialog when clicked on File->Close
        AppWindow.showExitConfirmationAlert();
    }

    @FXML
    protected void showCreateOrderDialog() { // Opening Dialog to create order (with all fields to be filled)

        DialogWindow dialogWindow = new DialogWindow(managerBorderPane.getScene().getWindow(), "This is create order dialog",
                "Create new order", "fxml/addOrderDialog-view.fxml");
        dialogWindow.prepareDialog(true, true);
        Optional<ButtonType> result = dialogWindow.launchDialogResult();
        FXMLLoader dialogLoader = dialogWindow.getDialogLoader();

        // Processing results of dialog window

        if((result.isPresent()) && (result.get() == ButtonType.OK)) {
            AddOrderDialogController controller = dialogLoader.getController();
            Order newOrder = controller.processOrder();
            Category selectedCategory = controller.getSelectedCategory();
            orderData.addOrder(newOrder); // Adding, newly created order, into OrderData instance
            ordersList.add(newOrder); // Adding to FXCollections' observableArrayList
            ordersListView.getSelectionModel().select(newOrder); // Selecting, on ListView, newly created order

            // Adding order into SQL DB

            try {
                orderData.addOrderToSqlDatabase(newOrder, selectedCategory);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void showEditOrderDialog() { // Opening Dialog to edit order (with all fields to be filled)

        DialogWindow dialogWindow = new DialogWindow(managerBorderPane.getScene().getWindow(), "This is edit order dialog",
                "Modify order", "fxml/editOrderDialog-view.fxml");
        dialogWindow.prepareDialog(true, true);
        EditOrderDialogController editOrderDialogController = dialogWindow.getDialogLoader().getController();
        editOrderDialogController.processOrder(currentlySelectedOrder);
        Optional<ButtonType> result = dialogWindow.launchDialogResult();
        FXMLLoader dialogLoader = dialogWindow.getDialogLoader();

        // Processing results of dialog window

        if((result.isPresent()) && (result.get() == ButtonType.OK)) {
            editOrderDialogController.modifyOrder();
            Category selectedCategory = editOrderDialogController.getSelectedCategory();
            currentlySelectedOrder.setTitle(editOrderDialogController.getNewTitle());
            currentlySelectedOrder.setCategory(editOrderDialogController.getNewCategory());
            currentlySelectedOrder.setPrice(editOrderDialogController.getNewPrice());
            currentlySelectedOrder.setDescription(editOrderDialogController.getNewDescription());
            ordersListView.refresh();
            ordersListView.getSelectionModel().select(currentlySelectedOrder); // Selecting, on ListView, modified order

            // Adding order into SQL DB

            try {
                orderData.editOrderInSqlDatabase(currentlySelectedOrder, selectedCategory);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void showCategoryManagerWindow() { // Opening category manager window

        // Initializing, declaring and processing Dialog Window to manage categories

        Stage categoryManagerStage = new Stage();
        try {
            AppWindow categoryManagerWindow = new AppWindow(categoryManagerStage, "categoryManager-view.fxml", 300, 400,
                    0.5, "Category manager", false, true);
            categoryManagerWindow.resizableWindow(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showOrderDetailsInPane(Order selectedOrder) {
        titleOrderDetailField.setText(selectedOrder.getTitle());
        idOrderDetailField.setText(String.valueOf(selectedOrder.getId()));
        categoryOrderDetailField.setText(selectedOrder.getCategory());
        priceOrderDetailField.setText(String.valueOf(selectedOrder.getPrice()));
        descriptionOrderDetailField.setText(selectedOrder.getDescription());
        // TODO: Add dates update
    }

    private void updateListViewCellFactory() { // Updating way of CellFactory's behaviour
        ordersListView.setCellFactory(param -> new ListCell<>() { // Utilizing Cell Factory to ensure ListView is not named by object's hash but id + title
            @Override
            protected void updateItem(Order order, boolean b) {
                super.updateItem(order, b);

                if(b || order == null || order.getTitle() == null) {
                    setText(null);
                } else {
                    setText("id " + order.getId() + ": " + order.getTitle()); // Setting up pattern of order's name in the ListView
                }
            }
        });
    }
}