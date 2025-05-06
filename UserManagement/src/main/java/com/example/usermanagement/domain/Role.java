package com.example.usermanagement.domain;

import java.util.UUID;

public class Role {
    private UUID id;
    private String roleName;

    public Role(UUID id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public UUID getId() { return id; }
    public String getRoleName() { return roleName; }
}
