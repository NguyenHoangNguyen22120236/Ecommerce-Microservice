package com.ecommercemicroservice.order.dtos;

import com.ecommercemicroservice.order.models.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private String userId;
    private OrderStatus status;
    private List<OrderItemDTO> orderItems;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}
