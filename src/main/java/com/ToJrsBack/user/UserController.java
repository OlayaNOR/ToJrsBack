package com.ToJrsBack.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public UserResponse getProfile() {
        return userService.getProfile();
    }

    @PutMapping("/me")
    public UserResponse updateProfile(@RequestBody UpdateProfileRequest req) {
        return userService.updateProfile(req);
    }

    @PutMapping("/me/password")
    public void updatePassword(@RequestBody UpdatePasswordRequest req) {
        userService.updatePassword(req);
    }

    @DeleteMapping("/me")
    public void deleteAccount() {
        userService.deleteAccount();
    }
}
