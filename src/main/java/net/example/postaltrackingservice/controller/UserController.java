package net.example.postaltrackingservice.controller;

import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.model.dto.UserDto;
import net.example.postaltrackingservice.model.dto.UserReadDto;
import net.example.postaltrackingservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController implements AdminController {

    private final UserService userService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserReadDto register(@RequestBody @Validated UserDto userDto) {
        return userService.register(userDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{userId}")
    public ResponseEntity<?> findUserById(@PathVariable long userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
