package com.example.employeeallocation.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "employee-allocation", groupId = "employee-group")
    public void listen(ConsumerRecord<String, String> record) {
        logger.info("Received Message: {}", record.value());
    }

}
