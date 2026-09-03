package com.pelegrin.job_application_tracker.config;

import org.springframework.stereotype.Component;

import com.pelegrin.job_application_tracker.entity.users.Permission;
import com.pelegrin.job_application_tracker.entity.users.Role;
import com.pelegrin.job_application_tracker.repository.users.PermissionRepository;
import com.pelegrin.job_application_tracker.repository.users.RoleRepository;

import jakarta.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;

@Component
public class SecurityDataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public SecurityDataSeeder(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        seedPermissions();
        seedAdminRole();
    }

    private void seedPermissions() {
        for (String name : PermissionConstants.ALL) {

            if (!permissionRepository.existsByName(name)) {
                permissionRepository.save(new Permission(name));
            }
        }
    }

    private void seedAdminRole() {

        Role admin = roleRepository
                .findByCode("ADMIN")
                .orElseGet(() -> roleRepository.save(
                        new Role(
                                "ADMIN",
                                "Administrador")));

        permissionRepository
                .findAll()
                .forEach(admin::addPermission);

        roleRepository.save(admin);
    }

}
