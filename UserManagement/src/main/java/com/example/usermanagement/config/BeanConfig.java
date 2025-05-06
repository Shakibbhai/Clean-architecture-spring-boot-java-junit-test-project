package com.example.usermanagement.config;

import com.example.usermanagement.Application.RoleService;
import com.example.usermanagement.Application.UserService;
import com.example.usermanagement.Application.interfaces.RoleRepository;
import com.example.usermanagement.Application.interfaces.UserRepository;
import com.example.usermanagement.infrastructure.controller.RoleController;
import com.example.usermanagement.infrastructure.controller.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Configuration

public class BeanConfig {
    @Bean
    public UserService userService(UserRepository userRepository, RoleRepository roleRepository) {
        return new UserService(userRepository, roleRepository);
    }
    @Bean
    public RoleService roleService(RoleRepository roleRepository) {
        return new RoleService(roleRepository);
    }
    @Bean
    public RoleController roleController(RoleService roleService) {
        return new RoleController(roleService);
    }
    @Bean
    public UserController userController(UserService userService) {
        return new UserController(userService);
    }

}
