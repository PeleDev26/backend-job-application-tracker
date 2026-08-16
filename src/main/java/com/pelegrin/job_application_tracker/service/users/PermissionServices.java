package com.pelegrin.job_application_tracker.service.users;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pelegrin.job_application_tracker.entity.users.Permission;
import com.pelegrin.job_application_tracker.repository.users.PermissionsRepository;

@Service
public class PermissionServices {

    private final PermissionsRepository repository;

    public PermissionServices(PermissionsRepository repository) {
        this.repository = repository;
    }


    // methods 
    public List<Permission> getPermissions() {
        return repository.findAll();
    }
    
}
