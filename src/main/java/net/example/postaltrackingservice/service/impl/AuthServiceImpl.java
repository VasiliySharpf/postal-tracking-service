package net.example.postaltrackingservice.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.exception.JwtAuthenticationException;
import net.example.postaltrackingservice.jwt.JwtRequest;
import net.example.postaltrackingservice.jwt.JwtResponse;
import net.example.postaltrackingservice.jwt.JwtTokenProvider;
import net.example.postaltrackingservice.model.dto.UserReadDto;
import net.example.postaltrackingservice.model.enums.UserStatus;
import net.example.postaltrackingservice.service.AuthService;
import net.example.postaltrackingservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Override
    public JwtResponse login(@NotNull JwtRequest request) {
        UserReadDto user = userService.findByUsername(request.username())
                .orElseThrow(() -> new JwtAuthenticationException("Пользователь не найден по имени: " + request.username()));

        if (user.status() != UserStatus.ACTIVE) {
            throw new JwtAuthenticationException(
                    String.format("Пользователь '%s' деактивирован или помечен на удаление", request.username()));

        } else if (passwordEncoder.matches(request.rawPassword(), user.password())) {
            String accessToken = jwtTokenProvider.createToken(user.username(), user.roles());
            return new JwtResponse(request.username(), accessToken);

        } else {
            throw new JwtAuthenticationException(
                    String.format("Пользователь '%s' ввел неправильный пароль", request.username()));
        }
    }
}
