package com.example.usermanagement.infrastructure.controller;

import com.example.usermanagement.Application.UserService;
import com.example.usermanagement.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UUID> createUser(@Valid @RequestBody CreateUserRequest request) {
        UUID userId = userService.createUser(request.name(), request.email());
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/{userId}/assign-role/{roleId}")
    public ResponseEntity<String> assignRole(@PathVariable UUID userId, @PathVariable UUID roleId) {
        userService.assignRole(userId, roleId);
        return ResponseEntity.ok("Role assigned");
    }
    @DeleteMapping("/{userId}/remove-role/{roleId}")
    public ResponseEntity<String> removeRole(@PathVariable UUID userId, @PathVariable UUID roleId) {
        userService.removeRole(userId, roleId);
        return ResponseEntity.ok("Role removed");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    public record CreateUserRequest(@NotBlank String name, @Email String email) {}
}
