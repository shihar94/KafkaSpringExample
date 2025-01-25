package com.example.kafkaspring;

import com.example.kafkaspring.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {


    private KafkaTemplate<String, User> kafkaTemplate;

    @Autowired
    public KafkaController(@Qualifier("userKafkaTemplate") KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody User user) {
        if (user == null) {return ResponseEntity.badRequest().build();}
        kafkaTemplate.send("example-topic",user.getEmail(),user);
        return ResponseEntity.accepted().body("Message sent to be published");
    }
}