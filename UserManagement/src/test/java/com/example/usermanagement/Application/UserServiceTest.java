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


        when(userRepository.findById(userId)).thenAnswer(invocation -> Optional.of(new User(
                testUser.getId(), testUser.getName(), testUser.getEmail()))); // Return fresh copy
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

        verify(userRepository).save(argThat(user ->
                user.getRoles().contains(testRole)
        ));
    }

    @Test
    void testRemoveRole() {
        // Assign role first
        testUser.assignRole(testRole);
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        userService.removeRole(userId, roleId);

        assertFalse(testUser.getRoles().contains(testRole));
        verify(userRepository).save(testUser);
    }

    @Test
    void testGetUser_Success() {
        User result = userService.getUser(userId);

        assertEquals(userId, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void testGetUser_NotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.getUser(userId));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testAssignRole_UserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.assignRole(userId, roleId));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testAssignRole_RoleNotFound() {
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.assignRole(userId, roleId));
        assertEquals("Role not found", ex.getMessage());
    }

    @Test
    void testRemoveRole_UserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.removeRole(userId, roleId));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testRemoveRole_RoleNotFound() {
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.removeRole(userId, roleId));
        assertEquals("Role not found", ex.getMessage());
    }
}
