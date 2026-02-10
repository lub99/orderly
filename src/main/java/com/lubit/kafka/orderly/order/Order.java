package com.lubit.kafka.orderly.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Order {

    private String id;
    private String customerId;
    private List<String> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public static Order newOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.id = UUID.randomUUID().toString();
        order.customerId = request.getCustomerId();
        order.items = request.getItems();
        order.totalAmount = request.getTotalAmount();
        order.status = OrderStatus.NEW;
        order.createdAt = Instant.now();
        order.updatedAt = order.createdAt;
        return order;
    }

    public void applyUpdate(UpdateOrderRequest request) {
        if (request.getItems() != null) {
            this.items = request.getItems();
        }
        if (request.getTotalAmount() != null) {
            this.totalAmount = request.getTotalAmount();
        }
        this.status = request.getStatus();
        this.updatedAt = Instant.now();
    }
}

