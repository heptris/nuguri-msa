package com.nuguri.dealservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuguri.dealservice.dto.deal.DealDetailDto;
import com.nuguri.dealservice.dto.deal.DealLoginDetailDto;
import com.nuguri.dealservice.dto.deal.ToMemberTempDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public ToMemberTempDto send(String topic, ToMemberTempDto toMemberTempDto){
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try{
            jsonInString = mapper.writeValueAsString(toMemberTempDto);
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the Deal microservice: " + toMemberTempDto);

        return toMemberTempDto;
    }

}
