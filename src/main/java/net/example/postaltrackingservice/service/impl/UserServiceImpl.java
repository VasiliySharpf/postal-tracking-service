package net.example.postaltrackingservice.service.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.postaltrackingservice.exception.UserRegistrationException;
import net.example.postaltrackingservice.mapper.UserMapper;
import net.example.postaltrackingservice.model.dto.UserDto;
import net.example.postaltrackingservice.model.dto.UserReadDto;
import net.example.postaltrackingservice.model.enums.UserStatus;
import net.example.postaltrackingservice.repository.UserRepository;
import net.example.postaltrackingservice.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static net.example.postaltrackingservice.model.enums.UserRole.mapToAuthorities;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Transactional
    @Override
    public UserReadDto register(@NotNull UserDto userDto) {

        userRepository.findByUsername(userDto.username())
                .ifPresent(user -> {
                    throw new UserRegistrationException("Пользователь с указанным именем уже существует: " + userDto.username());
                });

        return Optional.of(userDto)
                .map(userMapper::map)
                .map(userRepository::save)
                .map(userMapper::mapToReadDto)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToReadDto);
    }

    @Override
    public Optional<UserReadDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::mapToReadDto);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(@NotBlank String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getStatus().equals(UserStatus.ACTIVE),
                        true,
                        true,
                        true,
                        mapToAuthorities(user.getRoles())

                ))
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден по имени: " + username));
    }
}
