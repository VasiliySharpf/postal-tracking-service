package net.example.postaltrackingservice.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtRequest(@JsonProperty("username")
                         String username,
                         @JsonProperty("password")
                         String rawPassword) {
}
