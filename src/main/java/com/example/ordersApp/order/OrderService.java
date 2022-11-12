package com.example.ordersApp.order;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    boolean createOrder(OrderEntity orderEntity, Long userId);
    boolean deleteOrder(Long orderId, Long userId);
    void updateOrder(OrderEntity orderEntity, Long orderId);
    boolean changeCompletionStatus(Long orderId, Long userId);
    List<OrderEntity> getOrdersByWeek(Long userId, LocalDateTime dateTime);
    List<OrderEntity> getOrdersByDay(Long userId, LocalDateTime dateTime);
    OrderEntity getOrder(Long orderId, Long userId);
}
