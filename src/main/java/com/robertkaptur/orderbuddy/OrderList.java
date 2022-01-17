package com.robertkaptur.orderbuddy;

import java.util.ArrayList;

public class OrderList {

    ArrayList<Order> listOfOrders;

    public OrderList() {
        listOfOrders = new ArrayList<>();
    }

    public boolean addOrderToList(Order order) {
        return listOfOrders.add(order);
    }

    public boolean deleteOrderFromList(Order order) {
        return listOfOrders.remove(order);
    }

    public ArrayList<Order> getListOfOrders() {
        return listOfOrders;
    }
}
