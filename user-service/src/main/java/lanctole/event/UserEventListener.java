package lanctole.event;

import lanctole.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final KafkaMessageService kafkaMessageService;

    @EventListener
    public void handleUserEvent(UserEvent event) {
        log.info("Handling Spring event: {}", event);
        kafkaMessageService.sendUserEvent(event.eventType(), event.email());
    }
}
