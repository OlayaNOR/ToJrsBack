package com.ToJrsBack.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponse getProfile() {
        User user = getCurrentUser();

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .profileImageUrl(user.getProfileImageUrl())
                .role(user.getRole().name())
                .build();
    }

    public UserResponse updateProfile(UpdateProfileRequest req) {

        User user = getCurrentUser();

        if (req.getName() != null && !req.getName().isBlank()) {
            user.setName(req.getName());
        }

        if (req.getProfileImageUrl() != null) {
            user.setProfileImageUrl(req.getProfileImageUrl());
        }

        User updated = userRepository.save(user);

        return UserResponse.builder()
                .id(updated.getId())
                .email(updated.getEmail())
                .name(updated.getName())
                .profileImageUrl(updated.getProfileImageUrl())
                .role(updated.getRole().name())
                .build();
    }

    public void updatePassword(UpdatePasswordRequest req) {

        User user = getCurrentUser();

        if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        if (req.getNewPassword().length() < 6) {
            throw new RuntimeException("The new password is too short");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteAccount() {
        User user = getCurrentUser();
        userRepository.delete(user);
    }
}
