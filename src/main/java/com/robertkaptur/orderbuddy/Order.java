package com.robertkaptur.orderbuddy;

public class Order {

    // Auto-ID counter
    private static int idCounter; // Static counter, as it is increasing per each created object in this class

    // Fields
    private String title;
    private final int id; // Will be auto-id, hence auto-assigned id from idCounter or, during import, from db - it's final, because only assigned during obj init
    private String category; // Will be just a String-type field for pre-alpha
    private double price;
    private String description;
    private String dateOfOrder; // Should be in ISO8601 strings for SQL db
    private String dateOfDelivery; // Should be in ISO8601 strings for SQL db


    // Constructor for standard creation
    public Order(String title, String category, double price, String description, String dateOfOrder, String dateOfDelivery) {
        this.title = title;
        this.id = idCounter;
        setIdCounter(idCounter + 1);
        this.category = category;
        this.price = price;
        this.description = description;
        this.dateOfOrder = dateOfOrder;
        this.dateOfDelivery = dateOfDelivery;
    }

    // Constructor for loaded db
    public Order(String title, String category, double price, String description, String dateOfOrder, String dateOfDelivery, int id) {
        this.title = title;
        this.id = id;
        this.category = category;
        this.price = price;
        this.description = description;
        this.dateOfOrder = dateOfOrder;
        this.dateOfDelivery = dateOfDelivery;

        // Checker for idCounter after importing db to ensure that it's higher than last id from import
        if(idCounter <= id) {
            setIdCounter(id + 1);
        }
    }

    // Getters & Setters

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        Order.idCounter = idCounter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() { // Only Getter for ID, no Setter due to Auto-ID policy
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(String dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }
}
