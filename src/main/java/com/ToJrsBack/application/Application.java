package com.ToJrsBack.application;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.ToJrsBack.job.Job;
import com.ToJrsBack.junior.Junior;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"junior_id", "job_id"})
})
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "junior_id", nullable = false)
    private Junior junior;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = ApplicationStatus.APPLIED;
    }

}
