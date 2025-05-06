package com.example.usermanagement.Application;

import com.example.usermanagement.Application.interfaces.RoleRepository;
import com.example.usermanagement.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RoleService Unit Tests")
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private final String validRoleName = "ADMIN";

    @Test
    @DisplayName("Should successfully create a role with valid name")
    void createRole_WithValidName_ReturnsUUID() {

        Role mockRole = new Role(UUID.randomUUID(), validRoleName);
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);


        UUID result = roleService.createRole(validRoleName);


        assertNotNull(result, "Should return a non-null UUID");
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    @DisplayName("Should persist the role when creating")
    void createRole_WithValidName_PersistsRole() {

        when(roleRepository.save(any(Role.class))).thenReturn(new Role(UUID.randomUUID(), validRoleName));


        roleService.createRole(validRoleName);


        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    @DisplayName("Should generate new UUID for each role")
    void createRole_Always_GeneratesNewUUID() {

        when(roleRepository.save(any(Role.class)))
                .thenReturn(new Role(UUID.randomUUID(), validRoleName))
                .thenReturn(new Role(UUID.randomUUID(), "USER"));


        UUID result1 = roleService.createRole(validRoleName);
        UUID result2 = roleService.createRole("USER");


        assertNotEquals(result1, result2, "Each role should get a unique UUID");
    }

    @Test
    @DisplayName("Should throw exception when role name is blank")
    void createRole_WithBlankName_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> roleService.createRole("")
        );

        assertEquals("Role name cannot be blank", exception.getMessage());
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    @DisplayName("Should throw exception when role name is null")
    void createRole_WithNullName_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> roleService.createRole(null)
        );

        assertEquals("Role name cannot be blank", exception.getMessage());
        verify(roleRepository, never()).save(any(Role.class));
    }
}