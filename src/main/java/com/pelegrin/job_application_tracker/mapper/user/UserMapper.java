package com.pelegrin.job_application_tracker.mapper.user;

import org.springframework.stereotype.Component;

import com.pelegrin.job_application_tracker.dto.user.UserResponse;
import com.pelegrin.job_application_tracker.entity.users.User;

@Component
public class UserMapper {
    

    public UserResponse toResponse(User user) {
        
        return new UserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getRut(),
            user.getEmail(),
            user.getPhone(),
            user.getIsActive(),
            // user.getRole().getId(),
            user.getRole().getName(),
            // user.getCity().getId(),
            user.getCity().getName()
        );
    }
}
