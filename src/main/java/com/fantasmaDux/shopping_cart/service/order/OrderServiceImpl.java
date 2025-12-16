package com.fantasmaDux.shopping_cart.service.order;

import com.fantasmaDux.shopping_cart.api.dto.OrderDto;
import com.fantasmaDux.shopping_cart.api.exception.OrderNotFoundException;
import com.fantasmaDux.shopping_cart.service.cart.CartService;
import com.fantasmaDux.shopping_cart.store.enums.OrderStatus;
import com.fantasmaDux.shopping_cart.store.model.Cart;
import com.fantasmaDux.shopping_cart.store.model.Order;
import com.fantasmaDux.shopping_cart.store.model.OrderItem;
import com.fantasmaDux.shopping_cart.store.model.Product;
import com.fantasmaDux.shopping_cart.store.repository.OrderRepository;
import com.fantasmaDux.shopping_cart.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setOrderTotalAmount(calculateTotalAmount(orderItems));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return savedOrder;
    }


    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        order.setUser(cart.getUser());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(UUID orderId) {
        return orderRepository.findById(orderId).map(this :: convertToOrderDto).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(UUID userId) {
        return orderRepository.findByUserId(userId).stream().map(this :: convertToOrderDto).collect(Collectors.toList());
    }

    private OrderDto convertToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
