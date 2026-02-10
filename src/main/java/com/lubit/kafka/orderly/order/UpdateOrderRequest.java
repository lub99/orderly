package com.lubit.kafka.orderly.order;

import java.math.BigDecimal;
import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateOrderRequest {

    private List<String> items;

    @PositiveOrZero
    private BigDecimal totalAmount;

    @Nonnull
    private OrderStatus status;
}

