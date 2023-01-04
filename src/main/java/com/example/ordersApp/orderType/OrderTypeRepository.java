package com.example.ordersApp.orderType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderTypeRepository extends CrudRepository<OrderTypeEntity, Long> {
    @Query(value = "SELECT * FROM order_types WHERE user_id = :userId", nativeQuery = true)
    List<OrderTypeEntity> getAllOrderTypesByUserId(Long userId);

    @Query(value = "SELECT * FROM order_types WHERE id = :orderTypeId", nativeQuery = true)
    Optional<OrderTypeEntity> getOrderType(Long orderTypeId);
}
