package net.example.postaltrackingservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import net.example.postaltrackingservice.model.enums.PostalItemType;

public record PostalItemCreateDto(
        @NotNull
        PostalItemType type,
        @NotBlank
        String recipient,
        @NotBlank
        @Size(min = 6, max = 20, message
                = "Длина индекса не должна быть меньше 6 и больше 20 символов")
        String indexOfRecipient,
        @NotBlank
        String addressOfRecipient) {
}
