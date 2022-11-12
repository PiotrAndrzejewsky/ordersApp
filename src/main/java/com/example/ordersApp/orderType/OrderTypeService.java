package com.example.ordersApp.orderType;

import java.util.List;

public interface OrderTypeService {

    boolean createNewOrderType(OrderTypeEntity orderType);
    List<OrderTypeEntity> getAllOrderTypesByUserId(Long userId);
    OrderTypeEntity getOrderTypeEntity(Long orderTypeId, Long userId);
    boolean deleteOrderType(Long orderTypeId, Long userId);
}
