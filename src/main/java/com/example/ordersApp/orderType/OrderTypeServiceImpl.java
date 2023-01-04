package com.example.ordersApp.orderType;

import com.example.ordersApp.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderTypeServiceImpl implements OrderTypeService {

    @Autowired
    private OrderTypeRepository mOrderTypeRepository;

    @Autowired
    private OrderRepository mOrderRepository;

    @Override
    public boolean createNewOrderType(OrderTypeEntity orderType) {
        try {
            mOrderTypeRepository.save(orderType);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<OrderTypeEntity> getAllOrderTypesByUserId(Long userId) {
        return mOrderTypeRepository.getAllOrderTypesByUserId(userId);
    }

    @Override
    public OrderTypeEntity getOrderTypeEntity(Long orderTypeId, Long userId) {
        try {
            OrderTypeEntity orderType = mOrderTypeRepository.getOrderType(orderTypeId).orElseThrow(IllegalArgumentException::new);
            if (!orderType.getUserId().equals(userId)) {
                throw new IllegalArgumentException();
            }
            return orderType;
        }
        catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean deleteOrderType(Long orderTypeId, Long userId) {
        try {
            OrderTypeEntity orderType = mOrderTypeRepository.getOrderType(orderTypeId).orElseThrow(IllegalArgumentException::new);
            if (!orderType.getUserId().equals(userId)) {
                throw new IllegalArgumentException();
            }
            mOrderRepository.deleteAllByOrderTypeId(orderTypeId, userId);
            mOrderTypeRepository.deleteById(orderTypeId);
        }
        catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }
}
