package com.lubit.kafka.orderly.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class OrderEvent {

    private OrderEventType type;
    private String orderId;
    private String customerId;
    private List<String> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private OrderStatus previousStatus;
    private Instant occurredAt;
    private String notificationMessage;

    public static OrderEvent forCreated(Order order) {
        OrderEvent event = baseFromOrder(order);
        event.type = OrderEventType.ORDER_CREATED;
        event.notificationMessage = "Order " + order.getId() + " created for customer " + order.getCustomerId();
        return event;
    }

    public static OrderEvent forUpdated(Order order, OrderStatus previousStatus) {
        OrderEvent event = baseFromOrder(order);
        event.type = OrderEventType.ORDER_UPDATED;
        event.previousStatus = previousStatus;
        event.notificationMessage =
                "Order " + order.getId() + " updated from " + previousStatus + " to " + order.getStatus();
        return event;
    }

    public static OrderEvent forDeleted(Order order) {
        OrderEvent event = baseFromOrder(order);
        event.type = OrderEventType.ORDER_DELETED;
        event.notificationMessage = "Order " + order.getId() + " deleted";
        return event;
    }

    private static OrderEvent baseFromOrder(Order order) {
        OrderEvent event = new OrderEvent();
        event.orderId = order.getId();
        event.customerId = order.getCustomerId();
        event.items = order.getItems();
        event.totalAmount = order.getTotalAmount();
        event.status = order.getStatus();
        event.occurredAt = Instant.now();
        return event;
    }
}

