package com.ToJrsBack.application;

import com.ToJrsBack.company.Company;
import com.ToJrsBack.job.Job;
import com.ToJrsBack.job.JobRepository;
import com.ToJrsBack.junior.Junior;
import com.ToJrsBack.user.User;
import com.ToJrsBack.user.UserRepository;

import java.util.List;

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
                .juniorId(junior.getId())
                .juniorName(junior.getName())
                .jobTitle(job.getTitle())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    public List<ApplicationResponse> getByJob(Long jobId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!(user instanceof Company)) {
            throw new RuntimeException("Only companies can see the potulated juniors.");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found."));

        //Validar que el job pertenece a la empresa
        if (!job.getCompany().getId().equals(user.getId())) {
            throw new RuntimeException("Don't have permission.");
        }

        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(app -> ApplicationResponse.builder()
                        .id(app.getId())
                        .jobId(job.getId())
                        .juniorId(app.getJunior().getId())
                        .juniorName(app.getJunior().getName())
                        .jobTitle(app.getJob().getTitle())
                        .status(app.getStatus())
                        .createdAt(app.getCreatedAt())
                        .build()
                ).toList();
    }

    public ApplicationResponse updateStatus(Long applicationId, UpdateStatusRequest req) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!(user instanceof Company)) {
            throw new RuntimeException("Only companies can change the status");
        }

        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        //Validar que pertenece a su job
        if (!app.getJob().getCompany().getId().equals(user.getId())) {
            throw new RuntimeException("Don't have permission");
        }

        app.setStatus(req.getStatus());

        Application updated = applicationRepository.save(app);

        return ApplicationResponse.builder()
                .id(updated.getId())
                .jobId(updated.getJob().getId())
                .juniorId(updated.getJunior().getId())
                .juniorName(updated.getJunior().getName())
                .jobTitle(updated.getJob().getTitle())
                .status(updated.getStatus())
                .createdAt(updated.getCreatedAt())
                .build();
    }
    
}