package com.lubit.kafka.orderly.order;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(OrderEventPublisher.class);

    public static final String ORDERS_EVENTS_TOPIC = "orders.events";

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderCreated(Order order) {
        OrderEvent event = OrderEvent.forCreated(order);
        send(event);
    }

    public void publishOrderUpdated(Order order, OrderStatus previousStatus) {
        OrderEvent event = OrderEvent.forUpdated(order, previousStatus);
        send(event);
    }

    public void publishOrderDeleted(Order order) {
        OrderEvent event = OrderEvent.forDeleted(order);
        send(event);
    }

    private void send(OrderEvent event) {
        String key = event.getOrderId();
        log.info("Sending event {} for order {} to topic {}", event.getType(), key, ORDERS_EVENTS_TOPIC);
        kafkaTemplate.send(ORDERS_EVENTS_TOPIC, key, event);
    }
}

