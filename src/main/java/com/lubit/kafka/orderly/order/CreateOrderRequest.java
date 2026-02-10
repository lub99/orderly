package com.lubit.kafka.orderly.order;

import java.math.BigDecimal;
import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateOrderRequest {

    private String customerId;
    private List<String> items;
    @Nonnull
    @PositiveOrZero(message = "Order total amount cannot be negative")
    private BigDecimal totalAmount;
}

