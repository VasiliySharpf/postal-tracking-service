package net.example.postaltrackingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.jwt.JwtRequest;
import net.example.postaltrackingservice.jwt.JwtResponse;
import net.example.postaltrackingservice.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    @Operation(summary = "Аутентификация пользователя по логину и паролю и получение токена авторизации.")
    public JwtResponse login(@RequestBody JwtRequest request) {
        return authService.login(request);
    }

}
