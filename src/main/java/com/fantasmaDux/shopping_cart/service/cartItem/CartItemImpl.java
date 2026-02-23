package com.fantasmaDux.shopping_cart.service.cartItem;

import com.fantasmaDux.shopping_cart.api.exception.ProductNotFoundException;
import com.fantasmaDux.shopping_cart.service.cart.CartService;
import com.fantasmaDux.shopping_cart.service.product.ProductService;
import com.fantasmaDux.shopping_cart.store.model.Cart;
import com.fantasmaDux.shopping_cart.store.model.CartItem;
import com.fantasmaDux.shopping_cart.store.model.Product;
import com.fantasmaDux.shopping_cart.store.repository.CartItemRepository;
import com.fantasmaDux.shopping_cart.store.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(UUID cartId, UUID productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId()
                        .equals(productId)).findFirst().orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

        log.info("Added cart item to the cart");
        log.info("Product price: {}", product.getPrice());
        log.info("Quantity: {}", cartItem.getQuantity());
        log.info("CartItem unitPrice: {}", cartItem.getUnitPrice());

    }

    @Override
    public void removeItemFromCart(UUID cartId, UUID productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }


    @Override
    public void updateItemQuantity(UUID cartId, UUID productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId)).findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(productService.getProductById(productId).getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(UUID cartId, UUID productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
