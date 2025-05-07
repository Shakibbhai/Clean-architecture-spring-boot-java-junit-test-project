package com.example.usermanagement.domain;

import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

public class User {
    private UUID id;
    private String name;
    private String email;
    private Set<Role> roles = new HashSet<>();

    public User(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Set<Role> getRoles() { return roles; }

    public void assignRole(Role role) {
        roles.add(role);
    }
    public void removeRole(Role role) {
        roles.remove(role);
    }

}
