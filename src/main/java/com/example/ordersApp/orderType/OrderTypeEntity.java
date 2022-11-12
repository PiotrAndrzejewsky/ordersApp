package com.example.ordersApp.orderType;

import javax.persistence.*;

@Entity
@Table(name = "order_types")
public class OrderTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long mOrderTypeId;

    @Column(name = "user_id")
    private Long mUserId;

    @Column(name = "name")
    private String mName;

    public OrderTypeEntity(Long userId, String name) {
        mUserId = userId;
        mName = name;
    }

    public OrderTypeEntity() {
    }

    public Long getOrderTypeId() {
        return mOrderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        mOrderTypeId = orderTypeId;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
