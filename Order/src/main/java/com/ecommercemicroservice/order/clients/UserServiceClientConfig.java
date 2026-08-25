package com.ecommercemicroservice.order.clients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class UserServiceClientConfig {

    @Bean
    public UserServiceClient userServiceClient(
            @Qualifier("loadBalancedRestClientBuilder") RestClient.Builder builder) {
        RestClient restClient = builder
                .baseUrl("http://user-service")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
                    Optional.empty();
                })
                .build();
        RestClientAdapter clientAdapter = RestClientAdapter
                .create(restClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(clientAdapter)
                .build();

        UserServiceClient userServiceClient = factory.createClient(UserServiceClient.class);

        return userServiceClient;
    }
}
