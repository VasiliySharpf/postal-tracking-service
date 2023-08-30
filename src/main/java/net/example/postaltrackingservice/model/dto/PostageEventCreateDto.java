package net.example.postaltrackingservice.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostageEventCreateDto(
        @NotNull
        Long postalItemID,
        @Size(min = 6, max = 20, message
                = "Длина индекса не должна быть меньше 6 и больше 20 символов")
        String postOfficeCode) {
}
