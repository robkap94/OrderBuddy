package com.robertkaptur.orderbuddy;

public class Category {

    // Auto-ID counter
    private static int idCounter; // Static counter, as it is increasing per each created object in this class

    // Fields
    private final int id; // Will be auto-id, hence auto-assigned id from idCounter or, during import, from db - it's final, because only assigned during obj init
    private String categoryName;

    // Constructor for standard creation
    public Category(String categoryName) {
        this.id = idCounter;
        setIdCounter(idCounter + 1);
        this.categoryName = categoryName;
    }

    // Constructor for loaded db
    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;

        // Checker for idCounter after importing db to ensure that it's higher than last id from import
        if(idCounter <= id) {
            setIdCounter(id + 1);
        }

        // TODO: Delete after testing
        System.out.println("Created category with name: " + categoryName);
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public int getId() { // Only Getter for ID, no Setter due to Auto-ID policy
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public static void setIdCounter(int idCounter) {
        Category.idCounter = idCounter;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
