package com.ToJrsBack.application;

import lombok.Data;

@Data
public class UpdateStatusRequest {
    private ApplicationStatus status;
}