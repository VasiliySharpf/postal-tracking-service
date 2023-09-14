package net.example.postaltrackingservice.service;

import net.example.postaltrackingservice.model.dto.UserDto;
import net.example.postaltrackingservice.model.dto.UserReadDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserReadDto register(UserDto userDto);

    Optional<UserReadDto> findById(Long id);

    Optional<UserReadDto> findByUsername(String username);

}
