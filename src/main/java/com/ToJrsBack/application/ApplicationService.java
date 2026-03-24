package com.ToJrsBack.application;

import com.ToJrsBack.job.Job;
import com.ToJrsBack.job.JobRepository;
import com.ToJrsBack.junior.Junior;
import com.ToJrsBack.user.User;
import com.ToJrsBack.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    public ApplicationResponse apply(ApplicationRequest req) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!(user instanceof Junior)) {
            throw new RuntimeException("Only juniors can apply.");
        }

        Junior junior = (Junior) user;

        Job job = jobRepository.findById(req.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found."));

        applicationRepository.findByJuniorIdAndJobId(junior.getId(), job.getId())
                .ifPresent(a -> {
                    throw new RuntimeException("You already apply to this job.");
                });

        Application application = new Application();
        application.setJunior(junior);
        application.setJob(job);

        Application saved = applicationRepository.save(application);

        return ApplicationResponse.builder()
                .id(saved.getId())
                .jobId(job.getId())
                .jobTitle(job.getTitle())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}