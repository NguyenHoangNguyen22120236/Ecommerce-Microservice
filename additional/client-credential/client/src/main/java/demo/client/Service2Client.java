package demo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Service2Client {
    private final RestTemplate restTemplate;
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Value("${service2.url}")
    String service2Url;

    public Service2Client(RestTemplate restTemplate, OAuth2AuthorizedClientManager authorizedClientManager) {
        this.restTemplate = restTemplate;
        this.authorizedClientManager = authorizedClientManager;
    }

    public String fetchData(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String incomingToken = null;

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken){
            incomingToken = jwtAuthenticationToken.getToken().getTokenValue();
        }
        return "Fetch";
    }
}
