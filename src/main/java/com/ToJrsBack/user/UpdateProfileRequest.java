package com.ToJrsBack.user;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String name;
    private String profileImageUrl;
}