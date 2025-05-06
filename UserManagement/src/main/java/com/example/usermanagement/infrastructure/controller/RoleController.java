package com.example.usermanagement.infrastructure.controller;

import com.example.usermanagement.Application.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<UUID> createRole(@Valid @RequestBody CreateRoleRequest request) {
        UUID roleId = roleService.createRole(request.roleName());
        return ResponseEntity.ok(roleId);
    }

    public record CreateRoleRequest(@NotBlank String roleName) {}
}
