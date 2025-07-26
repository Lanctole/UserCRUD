package lanctole.service;

import lanctole.enums.EventType;

public interface KafkaMessageService {
    void sendUserEvent(EventType eventType, String email);
}
