package com.ToJrsBack.job;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByWorkingMode(WorkingMode workingMode);

    List<Job> findByCountryContainingIgnoreCase(String country);

    List<Job> findByCityContainingIgnoreCase(String city);

    List<Job> findByWorkingModeAndCountryContainingIgnoreCase(
            WorkingMode workingMode, String country);

    List<Job> findByWorkingModeAndCityContainingIgnoreCase(
            WorkingMode workingMode, String city);
}
