package com.ToJrsBack.application;

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
}