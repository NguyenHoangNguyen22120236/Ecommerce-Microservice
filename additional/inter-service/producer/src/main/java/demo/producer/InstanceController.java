package demo.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstanceController {

    @Value("${server.port}")
    private String port;

    private final String instanceName = "instanceName";

    @GetMapping("/instance-info")
    public String getInstance(){
        System.out.println("getInstance with port: " + port);
        return "getInstance";
    }
}
