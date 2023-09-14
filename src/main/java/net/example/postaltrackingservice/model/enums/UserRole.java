package net.example.postaltrackingservice.model.enums;

import jakarta.validation.constraints.NotNull;
import net.example.postaltrackingservice.model.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public enum UserRole implements GrantedAuthority {

    USER,
    ADMIN,
    POST_WORKER;

    @Override
    public String getAuthority() {
        return name();
    }

    public static UserRole getRoleByName(@NotNull String roleName) {
        return Arrays.stream(UserRole.values())
                .filter(role -> roleName.equalsIgnoreCase(role.getAuthority()))
                .findAny()
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("Роль по имени '%s' не найдена", roleName)));
    }

    public static List<UserRole> mapToAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> getRoleByName(role.getName()))
                .collect(Collectors.toList());
    }
}
