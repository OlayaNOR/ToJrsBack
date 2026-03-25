package com.ToJrsBack.application;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByJuniorIdAndJobId(Long juniorId, Long jobId);

    List<Application> findByJobId(Long jobId);

    List<Application> findByJuniorId(Long juniorId);

    List<Application> findByJuniorIdAndStatus(Long juniorId, ApplicationStatus status);
    
}
