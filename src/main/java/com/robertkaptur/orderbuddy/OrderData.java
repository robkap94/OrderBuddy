package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

public class OrderData {

    private final static OrderData instance = new OrderData();
    private final static String dbDir = "db";
    private final static String dbFile = "db.txt";
    private final static String dbFilename = dbDir + "/" + dbFile; // This will go to resources -> db -> db file
    private final static Path path = Paths.get(dbFilename);

    private DateTimeFormatter formatter;
    private DecimalFormat decimalFormatter = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US)); // To be utilized during saving db
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
    }

    public void deleteOrder(Order order) {
        listOfOrders.remove(order);
    }

    public void loadDatabase() throws IOException {
        if (Files.exists(path)) { // Check whether db file exists
        } else {
            return;
        }

        BufferedReader bufferedReader = Files.newBufferedReader(path);

        try {
            String orderInput;

            while((orderInput = bufferedReader.readLine()) != null) {
                String[] orderArgs = orderInput.split("\t");

                int id = Integer.parseInt(orderArgs[0]);
                String title = orderArgs[1];
                String category = orderArgs[2];
                Double price = Double.parseDouble(orderArgs[3]);
                String description = orderArgs[4];
                String dateOfOrder = orderArgs[5];
                String dateOfDelivery = orderArgs[6];

                Order importedOrder = new Order(title, category, price, description, dateOfOrder, dateOfDelivery, id);
                addOrder(importedOrder); // Adding order into OrderData's list (observableArrayList), which will be utilized by ListView
            }
        } finally { // Buffer closure
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

    }

    public void saveDatabase() throws IOException {

        try { // Check whether dir exists
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter bufferedWriter = Files.newBufferedWriter(path);

        try {
            Iterator<Order> iter = listOfOrders.iterator();
            while (iter.hasNext()) {
                Order order = iter.next();
                bufferedWriter.write(String.format("%d\t%s\t%s\t%s\t%s\t%s\t%s",
                        order.getId(),
                        order.getTitle(),
                        order.getCategory(),
                        decimalFormatter.format(order.getPrice()), // price is saved, as a string, with decFormatter (X.XX)
                        order.getDescription(),
                        // TODO: Ensure to change later these two below %s into actual date-format (Maybe it can be also saved like string but with format?
                        order.getDateOfOrder(),
                        order.getDateOfDelivery()));
                bufferedWriter.newLine();
            }
        } finally { // Buffer closure
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }
}
