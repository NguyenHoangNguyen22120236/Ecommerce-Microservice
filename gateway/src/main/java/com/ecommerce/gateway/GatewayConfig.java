package com.ecommerce.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public RedisRateLimiter  redisRateLimiter() {
        return new RedisRateLimiter(10, 20, 1);
    }

    @Bean
    public KeyResolver useKeyResolver() {
        return exchange ->
                Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                            .route("product-service",r -> r
                                    .path("/api/products/**")
                                    .uri("lb://PRODUCT-SERVICE"))
                            .route("user-service",r -> r
                                    .path("/api/users/**")
                                    .uri("lb://USER-SERVICE"))
                            .route("order-service",r -> r
                                    .path("/api/orders/**")
                                    .filters(f -> f.rewritePath("/eureka/main", "/"))
                                    .uri("lb://ORDER-SERVICE"))
                            .route("eureka-server",r -> r
                                    .path("/eureka/main")
                                    .uri("http//localhost:8761"))
                            .route("eureka-server-static",r -> r
                                    .path("/eureka/**")
                                    .filters(f -> f.rewritePath("/eureka/main", "/"))
                                    .uri("http//localhost:8761"))
                            .build();
    }
}
