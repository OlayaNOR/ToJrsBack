package com.ToJrsBack.job;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.ToJrsBack.company.Company;

@Data
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    @Enumerated(EnumType.STRING)
    private WorkingMode typeJob;

    @Column
    @Enumerated(EnumType.STRING)
    private KindJob kindJob; 

    @Column
    private String skills;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Boolean active = true;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
