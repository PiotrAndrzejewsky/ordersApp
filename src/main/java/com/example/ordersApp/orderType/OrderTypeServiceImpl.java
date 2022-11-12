package com.example.ordersApp.orderType;

import com.example.ordersApp.exceptions.OrderTypeNotFound;
import com.example.ordersApp.security.AuthHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderTypeServiceImpl implements OrderTypeService {

    @Autowired
    private OrderTypeRepository mOrderTypeRepository;

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
            mOrderTypeRepository.deleteById(orderTypeId);
        }
        catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }
}
