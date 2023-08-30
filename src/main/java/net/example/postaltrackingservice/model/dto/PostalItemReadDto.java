package net.example.postaltrackingservice.model.dto;

import net.example.postaltrackingservice.model.enums.PostalItemStatus;
import net.example.postaltrackingservice.model.enums.PostalItemType;

public record PostalItemReadDto(
        Long id,
        PostalItemType type,
        String recipient,
        String indexOfRecipient,
        String addressOfRecipient,
        PostalItemStatus postalItemStatus) {
}
