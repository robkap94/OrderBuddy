package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class OrderData {

    private final static OrderData instance = new OrderData();
    private final static String dbDir = "db";
    private final static String dbFile = "db.txt";
    private final static String dbFilename = dbDir + "/" + dbFile; // This will go to resources -> db -> db file

    private DateTimeFormatter formatter;
    private ObservableList<Order> listOfOrders = FXCollections.observableArrayList();

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
        System.out.println("Added order with title " + order.getTitle() + " to the OrderData list"); // Test sout TODO: Delete after tests
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

    public void saveDatabase() throws IOException {
        Path path = Paths.get(dbFilename);
        System.out.println("Abs " + path.toAbsolutePath().toString()); // TODO delete after tests

        try {
            System.out.println("Parent dir of db: " + path.getParent().toAbsolutePath().toString()); // TODO delete after tests
            Files.createDirectories(path.getParent()); // Check whether dir exists
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error during db's dir existence checking or creation after checking");
        }

        BufferedWriter bufferedWriter = Files.newBufferedWriter(path);

        try {

            Iterator<Order> iter = listOfOrders.iterator();
            while(iter.hasNext()) {
                Order order = iter.next();
                bufferedWriter.write(String.format("%d\t%s\t%s\t%.2f\t%s\t%s\t%s",
                        order.getId(),
                        order.getTitle(),
                        order.getCategory(),
                        order.getPrice(),
                        order.getDescription(),
                        // TODO: Ensure to change later these two below %s into actual date-format (Maybe it can be also saved like string but with format?
                        order.getDateOfOrder(),
                        order.getDateOfDelivery()));
                System.out.println("Added " + order.getTitle() + " into db file"); // test sout TODO: Delete after tests
                bufferedWriter.newLine();
            }
        } finally {
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }
}
