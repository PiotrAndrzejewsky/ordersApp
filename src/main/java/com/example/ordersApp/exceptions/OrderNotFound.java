package com.example.ordersApp.exceptions;

public class OrderNotFound extends RuntimeException {
    public OrderNotFound(Long orderId) {
        super("Order with id " + orderId + " does not exist");
    }
}
