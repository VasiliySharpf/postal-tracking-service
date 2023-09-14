package net.example.postaltrackingservice.mapper;

import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.model.dto.UserDto;
import net.example.postaltrackingservice.model.dto.UserReadDto;
import net.example.postaltrackingservice.model.entity.Role;
import net.example.postaltrackingservice.model.entity.User;
import net.example.postaltrackingservice.model.enums.UserRole;
import net.example.postaltrackingservice.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserDto, User> {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserDto userDto) {

        Set<String> roles = userDto.roles().stream()
                .map(UserRole::getAuthority)
                .collect(toSet());

        return User.builder()
                .username(userDto.username())
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .status(userDto.status())
                .roles(roleRepository.findAllByNameIn(roles))
                .build();
    }

    public UserReadDto mapToReadDto(User user) {

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        return new UserReadDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getStatus(),
                roles);

    }
}
