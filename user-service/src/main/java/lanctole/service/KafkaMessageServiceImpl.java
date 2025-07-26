package lanctole.service;

import lanctole.dto.UserEventDto;
import lanctole.enums.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessageServiceImpl implements KafkaMessageService {
    private final KafkaTemplate<String, UserEventDto> kafkaTemplate;

    @Value("${app.kafka.topics.user-events}")
    private String topic;

    @Override
    public void sendUserEvent(EventType eventType, String email) {
        UserEventDto event = new UserEventDto(eventType, email);
        kafkaTemplate.send(topic, event);
        log.info("Kafka event sent: {}", event);
    }
}
