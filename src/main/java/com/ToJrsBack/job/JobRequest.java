package com.ToJrsBack.job;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobRequest {
    
    private String title;
    private String description;
    private String country;
    private String city;
    private WorkingMode workingMode;
    private KindJob kindJob; 
    private String skills;


    public JobRequest(String title, String description, String country, 
        String city, WorkingMode workingMode, KindJob kindJob, String skills){
            this.title = title;
            this. description = description;
            this. country = country;
            this. city = city;
            this.workingMode = workingMode;
            this.kindJob =kindJob;
            this.skills = skills;
    }
}
