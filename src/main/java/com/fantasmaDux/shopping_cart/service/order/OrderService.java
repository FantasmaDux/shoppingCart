package com.fantasmaDux.shopping_cart.service.order;

import com.fantasmaDux.shopping_cart.api.dto.OrderDto;
import com.fantasmaDux.shopping_cart.store.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order placeOrder(UUID userId);
    OrderDto getOrder(UUID orderId);

    List<OrderDto> getUserOrders(UUID userId);
}
