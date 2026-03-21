package com.ToJrsBack.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/jobs")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/new")
    public void create(@RequestBody JobRequest req) {
        
        System.out.println("TITLE: " + req.getTitle());
        jobService.create(req);
    }
    
}
