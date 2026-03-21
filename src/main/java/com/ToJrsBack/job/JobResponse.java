package com.ToJrsBack.job;

import java.time.LocalDateTime;

import com.ToJrsBack.company.Company;

import lombok.Data;

@Data
public class JobResponse {

    private String title;
    private String description;
    private String country;
    private String city;
    private WorkingMode workingMode;
    private KindJob kindJob; 
    private String skills;
    private Company company;
    private LocalDateTime createdAt;
    private Boolean active;

    public JobResponse(String title, String description, String country, 
        String city, WorkingMode workingMode, KindJob kindJob, String skills, Company company){
            this.title = title;
            this. description = description;
            this. country = country;
            this. city = city;
            this.workingMode = workingMode;
            this.kindJob =kindJob;
            this.skills = skills;
            this.company = company;
            createdAt = LocalDateTime.now();
            active = true;
    }
    
}
