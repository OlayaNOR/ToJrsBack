package com.ToJrsBack.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@CrossOrigin
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public ApplicationResponse apply(@RequestBody ApplicationRequest req) {
        return applicationService.apply(req);
    }

    @GetMapping("/job/{jobId}")
    public List<ApplicationResponse> getByJob(@PathVariable Long jobId) {
        return applicationService.getByJob(jobId);
    }

    @PutMapping("/{id}/status")
    public ApplicationResponse updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest req) {
        return applicationService.updateStatus(id, req);
    }

    @GetMapping("/me")
    public List<ApplicationResponse> getMyApplications(
            @RequestParam(required = false) ApplicationStatus status) {
        return applicationService.getMyApplications(status);
    }
}