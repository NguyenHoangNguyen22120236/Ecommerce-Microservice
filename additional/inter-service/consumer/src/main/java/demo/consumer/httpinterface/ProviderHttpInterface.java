package demo.consumer.httpinterface;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProviderHttpInterface {

    @GetExchange("/instance")
    public String getInstance();
}
