package net.example.postaltrackingservice.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import net.example.postaltrackingservice.model.enums.UserStatus;

import java.util.List;


public record UserReadDto(Long id,
                          String username,
                          @JsonIgnore
                          String password,
                          String firstName,
                          String lastName,
                          String email,
                          UserStatus status,
                          List<String> roles) {
}
