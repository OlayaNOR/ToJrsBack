package com.ToJrsBack.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
}
