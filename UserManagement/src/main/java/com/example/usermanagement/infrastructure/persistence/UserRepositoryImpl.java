package com.example.usermanagement.infrastructure.persistence;

import com.example.usermanagement.Application.interfaces.UserRepository;
import com.example.usermanagement.domain.Role;
import com.example.usermanagement.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());

        Set<RoleJpaEntity> roleEntities = user.getRoles().stream().map(role -> {
            RoleJpaEntity r = new RoleJpaEntity();
            r.setId(role.getId());
            r.setRoleName(role.getRoleName());
            return r;
        }).collect(Collectors.toSet());

        entity.setRoles(roleEntities);
        jpaRepository.save(entity);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(entity -> {
            User user = new User(entity.getId(), entity.getName(), entity.getEmail());

            if (entity.getRoles() != null) {
                for (RoleJpaEntity roleEntity : entity.getRoles()) {
                    user.assignRole(new Role(roleEntity.getId(), roleEntity.getRoleName()));
                }
            }

            return user;
        });
    }
}
