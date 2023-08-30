package net.example.postaltrackingservice.model.dto;

import net.example.postaltrackingservice.model.enums.EventType;
import java.time.OffsetDateTime;

public record PostageEventReadDto(
        Long id,
        Long postalItemID,
        String postOfficeCode,
        String postOfficeName,
        OffsetDateTime eventDate,
        EventType event) {
}
