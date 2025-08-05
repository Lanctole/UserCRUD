package lanctole.dto;

import lanctole.enums.EventType;

public record UserEventDto(EventType eventType, String email) {}
