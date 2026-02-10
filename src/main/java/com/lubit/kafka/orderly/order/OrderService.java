package com.lubit.kafka.orderly.order;

import java.util.Collection;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderEventPublisher eventPublisher;

    public OrderService(OrderRepository repository, OrderEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(CreateOrderRequest request) {
        Order order = Order.newOrder(request);
        repository.save(order);
        eventPublisher.publishOrderCreated(order);
        return order;
    }

    public Order getOrder(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Collection<Order> listOrders() {
        return repository.findAll();
    }

    public Order updateOrder(String id, UpdateOrderRequest request) {
        Order order = getOrder(id);
        OrderStatus oldStatus = order.getStatus();
        order.applyUpdate(request);
        repository.save(order);
        eventPublisher.publishOrderUpdated(order, oldStatus);
        return order;
    }

    public void deleteOrder(String id) {
        Order order = getOrder(id);
        repository.deleteById(id);
        eventPublisher.publishOrderDeleted(order);
    }
}

