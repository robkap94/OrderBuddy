package com.robertkaptur.orderbuddy;

import java.util.ArrayList;

public class OrderList {

    // TODO: Double-check whether this class is required? At this point, I think it's redundant.
    //       In case of any requirement, this may be created in the future.

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
