package lanctole.event;

import lanctole.dto.UserEventDto;
import lanctole.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final EmailService emailService;

    @KafkaListener(topics = "${app.kafka.topics.user-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleUserEvent(UserEventDto event) {
        switch (event.eventType()) {
            case USER_CREATED -> emailService.sendAccountCreatedEmail(event.email());
            case USER_DELETED -> emailService.sendAccountDeletedEmail(event.email());
        }
    }
}
