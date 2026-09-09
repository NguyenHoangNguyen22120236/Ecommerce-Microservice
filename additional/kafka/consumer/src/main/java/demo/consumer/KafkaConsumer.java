package demo.consumer;

import demo.producer.RiderLocation;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "test-topic", groupId = "my-new-group")
    public void listen1(String message){

    }

    @KafkaListener(topics = "test-topic", groupId = "my-new-group-1")
    public void listen2(String message){

    }

    @KafkaListener(topics = "test-topic-new", groupId = "my-new-group-1")
    public void listenRiderLocation(RiderLocation riderLocation){

    }
}
