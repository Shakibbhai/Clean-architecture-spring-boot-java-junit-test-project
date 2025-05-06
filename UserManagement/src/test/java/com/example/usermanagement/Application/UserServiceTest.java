package com.example.usermanagement.Application;

import com.example.usermanagement.Application.interfaces.RoleRepository;
import com.example.usermanagement.Application.interfaces.UserRepository;
import com.example.usermanagement.domain.Role;
import com.example.usermanagement.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserService userService;

    private UUID userId;
    private UUID roleId;
    private User testUser;
    private Role testRole;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        userService = new UserService(userRepository, roleRepository);

        userId = UUID.randomUUID();
        roleId = UUID.randomUUID();
        testUser = new User(userId, "John Doe", "john@example.com");
        testRole = new Role(roleId, "ADMIN");

        // Default successful mocks
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(testRole));
    }

    @Test
    void testCreateUser() {
        String name = "Alice";
        String email = "alice@example.com";

        UUID createdId = userService.createUser(name, email);

        verify(userRepository).save(argThat(user ->
                user.getName().equals(name) &&
                        user.getEmail().equals(email) &&
                        user.getId().equals(createdId)
        ));
    }

    @Test
    void testAssignRole() {
        userService.assignRole(userId, roleId);

        assertTrue(testUser.getRoles().contains(testRole));
        verify(userRepository).save(testUser);
    }

    @Test
    void testGetUser_Success() {
        User result = userService.getUser(userId);

        assertEquals(testUser, result);
    }

    @Test
    void testGetUser_NotFound() {
        // Override mock to simulate user not found
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.getUser(userId));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testAssignRole_UserNotFound() {
        // Override mock to simulate user not found
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.assignRole(userId, roleId));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testAssignRole_RoleNotFound() {
        // Override mock to simulate role not found
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.assignRole(userId, roleId));
        assertEquals("Role not found", ex.getMessage());
    }
}
