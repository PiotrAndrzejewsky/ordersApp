package com.example.ordersApp.exceptions;

public class OrderTypeNotFound extends RuntimeException{
    public OrderTypeNotFound(Long orderTypeId) {
        super("OrderType with id " + orderTypeId + " does not exist");
    }
}
