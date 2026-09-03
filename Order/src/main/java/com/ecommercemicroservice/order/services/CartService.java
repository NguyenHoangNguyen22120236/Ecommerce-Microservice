package com.ecommercemicroservice.order.services;

import com.ecommercemicroservice.order.clients.ProductServiceClient;
import com.ecommercemicroservice.order.clients.UserServiceClient;
import com.ecommercemicroservice.order.dtos.CartItemRequest;
import com.ecommercemicroservice.order.dtos.ProductResponse;
import com.ecommercemicroservice.order.dtos.UserResponse;
import com.ecommercemicroservice.order.models.CartItem;
import com.ecommercemicroservice.order.repositories.CartItemRepository;
import com.ecommercemicroservice.order.repositories.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartItemRepository cartItemRepository;

    private final ProductServiceClient productServiceClient;

    private final UserServiceClient  userServiceClient;

    int attempt = 0;

    //@CircuitBreaker(name = "productService", fallbackMethod = "addToCartFallback")
    @Retry(name = "productService", fallbackMethod = "addToCartFallback")
    public boolean addToCart(Long userId, CartItemRequest request) {
        // Look for product
        ProductResponse productResponse = productServiceClient.getProductById(request.getProductId());
        if (productResponse == null)
           return false;

        if (productResponse.getStockQuantity() < request.getQuantity())
            return false;

        UserResponse userResponse = userServiceClient.getUserById(userId);
        if (userResponse == null){
            return false;
        }

        //
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if (userOpt.isEmpty())
//            return false;
//
//        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if (existingCartItem != null) {
            // Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create new cart item
           CartItem cartItem = new CartItem();
           cartItem.setUserId(userId);
           cartItem.setProductId(request.getProductId());
           cartItem.setQuantity(request.getQuantity());
           cartItem.setPrice(BigDecimal.valueOf(1000.00));
           cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean addToCartFallback(Long userId, CartItemRequest request, Exception exception) {
        exception.printStackTrace();
        return false;
    }

    public boolean deleteItemFromCart(Long userId, Long productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
