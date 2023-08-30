package net.example.postaltrackingservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostOfficeDto(
        @NotBlank
        @Size(min = 6, max = 20, message
                = "Длина индекса не должна быть меньше 6 и больше 20 символов")
        String index,
        @NotBlank
        String name,
        @NotBlank
        String address) {
}
