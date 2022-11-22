package com.birthdayemail.twilioemail.controller;

import com.birthdayemail.twilioemail.model.EmailRequest;
import com.birthdayemail.twilioemail.service.MailService;
import com.sendgrid.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@Log4j2
public class MailController {

    @Value("${topic.name.consumer")
    private String topicName;

    @Autowired
    private MailService mailService;

    @PostMapping(path = "/sendmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        Response response = mailService.sendEmail(emailRequest);
        if (response.getStatusCode() == 200 || response.getStatusCode() == 202) {
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }


    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload) {
        log.info("Topic: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partition: {}", payload.partition());
        log.info("Order: {}", payload.value());


        String emailString = payload.value();
        List<String> mailId = Arrays.asList(emailString);
        log.info("Array as list " + mailId.toString());

        for (String mails : mailId) {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(mails);
            Date date = new Date();
            Response response = mailService.sendEmail(emailRequest);
            if (response.getStatusCode() == 200 || response.getStatusCode() == 202) {
            } else {
                log.info("");
            }
        }


    }

}
