package com.example.ordersApp.order;

import com.example.ordersApp.exceptions.OrderNotFound;
import com.example.ordersApp.orderType.OrderTypeEntity;
import com.example.ordersApp.orderType.OrderTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    @Value("${app.secret-token}")
    private String secret = "";

    @Autowired
    private OrderRepository mOrderRepository;

    @Autowired
    private OrderTypeRepository mOrderTypeRepository;

    @Override
    public boolean createOrder(OrderEntity orderEntity, Long userId) {
        try {
            OrderTypeEntity orderType = mOrderTypeRepository.getOrderType(orderEntity.getOrderTypeId()).orElseThrow(IllegalArgumentException::new);
            if (!orderType.getUserId().equals(userId)) {
                throw new IllegalArgumentException();
            }
            mOrderRepository.save(orderEntity);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteOrder(Long orderId, Long userId) {
        try {
            OrderEntity order = mOrderRepository.getOrder(orderId).orElseThrow(IllegalArgumentException::new);
            if (!order.getUserId().equals(userId)) {
                throw new IllegalArgumentException();
            }
            mOrderRepository.deleteById(orderId);
        }
        catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public void updateOrder(OrderEntity orderEntity, Long orderId) {
        OrderEntity order = mOrderRepository.findById(orderId).orElseThrow(() -> new OrderNotFound(orderId));
        order.setOrderTypeId(orderEntity.getOrderTypeId());
        order.setTitle(orderEntity.getTitle());
        order.setDescription(orderEntity.getDescription());
        order.setPrice(orderEntity.getPrice());
        order.setClient(orderEntity.getClient());
        order.setQuantity(orderEntity.getQuantity());
        order.setPlannedCompletionDate(orderEntity.getPlannedCompletionDate());
    }

    @Override
    public boolean changeCompletionStatus(Long orderId, Long userId) {
        try {
            OrderEntity order = mOrderRepository.getOrder(orderId).orElseThrow(IllegalArgumentException::new);
            if (!order.getUserId().equals(userId)) {
                throw new IllegalArgumentException();
            }
            order.setCompleted(!order.isCompleted());
        }
        catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<OrderEntity> getOrdersByWeek(Long userId, LocalDateTime dateTime) {
        return mOrderRepository.getOrdersByWeek(userId, dateTime);
    }

    @Override
    public List<OrderEntity> getOrdersByDay(Long userId, LocalDateTime dateTime) {
        dateTime = dateTime.truncatedTo(ChronoUnit.DAYS);
        return mOrderRepository.getOrdersByDay(userId, dateTime);
    }

    @Override
    public OrderEntity getOrder(Long orderId, Long userId) {
        try {
            OrderEntity order = mOrderRepository.getOrder(orderId).orElseThrow(IllegalArgumentException::new);
            if (!order.getUserId().equals(userId)) {
                throw new IllegalArgumentException();
            }
            return order;
        }
        catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            return null;
        }
    }
}
