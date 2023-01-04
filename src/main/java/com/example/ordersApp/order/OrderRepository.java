package com.example.ordersApp.order;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    @Query(value = "SELECT * FROM orders WHERE id = :orderId", nativeQuery = true)
    Optional<OrderEntity> getOrder(Long orderId);

    @Query(value = "SELECT * FROM orders WHERE YEARWEEK(planned_completion_date, 1) = YEARWEEK(:dateTime, 1) AND user_id = :userId", nativeQuery = true)
    List<OrderEntity> getOrdersByWeek(Long userId, LocalDateTime dateTime);

    @Query(value = "SELECT * FROM orders WHERE DATE(planned_completion_date) = :dateTime AND user_id = :userId", nativeQuery = true)
    List<OrderEntity> getOrdersByDay(Long userId, LocalDateTime dateTime);

    @Modifying
    @Query(value = "DELETE FROM ORDERS WHERE order_type_id = :orderTypeId AND user_id = :userId", nativeQuery = true)
    void deleteAllByOrderTypeId(Long orderTypeId, Long userId);
}
