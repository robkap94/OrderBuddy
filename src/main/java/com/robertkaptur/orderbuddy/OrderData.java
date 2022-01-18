package com.robertkaptur.orderbuddy;

import javafx.collections.ObservableList;

import java.time.format.DateTimeFormatter;

public class OrderData {

    private static OrderData instance = new OrderData();
    private static String dbFilename = "db/db.txt";

    private DateTimeFormatter formatter;
    private ObservableList<Order> listOfOrders;

    private OrderData() {
        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    public static OrderData getInstance() {
        return instance;
    }

    public static String getDbFilename() {
        return dbFilename;
    }

    public ObservableList<Order> getListOfOrders() {
        return listOfOrders;
    }

    public void addOrder(Order order) {
        listOfOrders.add(order);
    }

    public void deleteOrder(Order order) {
        listOfOrders.remove(order);
    }

    public void loadDatabase() {
        /* TODO: Prepare loading sequence:
        0) Create buffered reader with path
        1) listOfOrders needs to be initialized as a ObservableArrayList from FXCollections
        2) Path object which will check (whether exists) and creates when required db file & will mark this path
        3) Load parts of object into separated vars
        4) Create object
        5) Add it into FXCollections.observableList (NOT observableArrayList)
        6) Add "finally" to close the bufferedReader

        PLEASE NOTICE TO REBUILD Dates in object creation:
        a) Date of Creation is CURRENT DATE in ISO_LOCAL_DATE_TIME
        b) Date of Delivery is NULL

        PS. I have created separated branch for this!!!
        TODO: Delete feature-33-OrderData branch later on*/
    }

    public void saveDatabase() {
        /*TODO: Prepare saving sequence:
        *  DO everything the same as in loadDatabase, but in opposite + utilize BufferedWriter*/
    }
}
