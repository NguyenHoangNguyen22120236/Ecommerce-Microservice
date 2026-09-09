package demo.authcodeflow;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String welcome(OAuth2AuthenticationToken token) {
        String email = token.getPrincipal().getAttributes().get("email").toString();
        return "Welcome " + token.getName() + email;
    }
}
