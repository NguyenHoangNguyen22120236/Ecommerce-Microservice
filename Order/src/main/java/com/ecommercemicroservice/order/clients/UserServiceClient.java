package com.ecommercemicroservice.order.clients;

import com.ecommercemicroservice.order.dtos.ProductResponse;
import com.ecommercemicroservice.order.dtos.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {
    @GetExchange("/api/users/{id}")
    UserResponse getUserById(@PathVariable Long id);
}
