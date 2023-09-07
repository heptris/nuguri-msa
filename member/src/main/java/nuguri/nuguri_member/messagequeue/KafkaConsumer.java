package nuguri.nuguri_member.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuguri.nuguri_member.domain.Member;
import nuguri.nuguri_member.exception.ex.CustomException;
import nuguri.nuguri_member.exception.ex.ErrorCode;
import nuguri.nuguri_member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static nuguri.nuguri_member.exception.ex.ErrorCode.MEMBER_NOT_FOUND;

@AllArgsConstructor
@Service
@Slf4j
public class KafkaConsumer {

    private final MemberRepository memberRepository;

    @Transactional
//    @KafkaListener(topics = "example-deal-topic")
    public void updateTemperature(String kafkaMessage){
        log.info("Kafka Message: -> " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        Integer sellerId = (Integer) map.get("sellerId");
        Integer buyerId = (Integer) map.get("buyerId");
        Optional<Member> seller = memberRepository.findById(sellerId.longValue());
        Optional<Member> buyer = memberRepository.findById(buyerId.longValue());
        if(seller != null && buyer != null){
            seller.get().changeTemperature(seller.get().getTemperature() + 1.5);
            buyer.get().changeTemperature(buyer.get().getTemperature() + 1.5);
        }else{
            throw new CustomException(MEMBER_NOT_FOUND);
        }

    }


    @Transactional
//    @KafkaListener(topics = "hobby-topic")
    public void updateTemperatureFromHobby(String kafkaMessage){
        System.out.println("here@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("Kafka Message: -> " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        Integer hobbyOwnerId = (Integer) map.get("hobbyOwnerId");
        Optional<Member> hobbyOwner = memberRepository.findById(hobbyOwnerId.longValue());
        if(hobbyOwner != null){
            hobbyOwner.get().changeTemperature(hobbyOwner.get().getTemperature() + 1.5);
        }else{
            throw new CustomException(MEMBER_NOT_FOUND);
        }

    }



}
