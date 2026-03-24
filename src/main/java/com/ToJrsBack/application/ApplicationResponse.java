package com.ToJrsBack.application;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
}