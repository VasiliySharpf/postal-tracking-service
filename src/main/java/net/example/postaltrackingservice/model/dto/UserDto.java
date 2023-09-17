package net.example.postaltrackingservice.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import net.example.postaltrackingservice.model.enums.UserRole;
import net.example.postaltrackingservice.model.enums.UserStatus;

import java.util.Set;


public record UserDto(@NotBlank
                      String username,
                      @NotNull
                      String firstName,
                      @NotNull
                      String lastName,
                      @NotNull
                      String email,
                      @NotBlank
                      String password,
                      @NotNull
                      UserStatus status,
                      @NotNull
                      @NotEmpty(message = "У пользователя должна быть указана хотя бы одна роль.")
                      Set<UserRole> roles) {
}
