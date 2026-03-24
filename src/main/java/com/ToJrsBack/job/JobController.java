package com.ToJrsBack.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/jobs")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/new")
    public ResponseEntity<String> create(@RequestBody JobRequest req) {
        jobService.create(req);
        return ResponseEntity.ok("Job created.");
    }

    @GetMapping("/filter")
    public List<Job> filter(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) WorkingMode mode
    ) {
        return jobService.filter(country, city, mode);
    }
    
}
