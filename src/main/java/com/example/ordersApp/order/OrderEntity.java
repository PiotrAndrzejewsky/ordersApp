package com.example.ordersApp.order;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long mOrderId;

    @Column(name = "user_id")
    private Long mUserId;

    @Column(name = "order_type_id")
    private Long mOrderTypeId;

    @Column(name = "price")
    private double mPrice;

    @Column(name = "title")
    private String mTitle;

    @Column(name = "description")
    private String mDescription;

    @Column(name = "client")
    private String mClient;

    @Column(name = "quantity")
    private int mQuantity;

    @Column(name = "planned_completion_date")
    private LocalDateTime mPlannedCompletionDate;

    @Column(name = "completed")
    private boolean mCompleted;

    public OrderEntity(Long userId, Long orderTypeId, double price, String title, String client, LocalDateTime plannedCompletionDate) {
        mUserId = userId;
        mOrderTypeId = orderTypeId;
        mPrice = price;
        mTitle = title;
        mClient = client;
        mPlannedCompletionDate = plannedCompletionDate;
    }

    public OrderEntity() {
    }

    public Long getOrderId() {
        return mOrderId;
    }

    public void setOrderId(Long orderId) {
        mOrderId = orderId;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public Long getOrderTypeId() {
        return mOrderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        mOrderTypeId = orderTypeId;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getClient() {
        return mClient;
    }

    public void setClient(String client) {
        mClient = client;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public LocalDateTime getPlannedCompletionDate() {
        return mPlannedCompletionDate;
    }

    public void setPlannedCompletionDate(LocalDateTime plannedCompletionDate) {
        mPlannedCompletionDate = plannedCompletionDate;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
