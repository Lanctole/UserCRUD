package lanctole.event;

import lanctole.enums.EventType;

public record UserEvent(String email, EventType eventType) {}

