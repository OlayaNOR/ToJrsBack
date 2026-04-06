package com.ToJrsBack.job;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {


    List<Job> findAll();
        
    List<Job> findByWorkingMode(WorkingMode workingMode);

    List<Job> findByCountryContainingIgnoreCase(String country);

    List<Job> findByCityContainingIgnoreCase(String city);

    List<Job> findByWorkingModeAndCountryContainingIgnoreCase(
            WorkingMode workingMode, String country);

    List<Job> findByWorkingModeAndCityContainingIgnoreCase(
            WorkingMode workingMode, String city);

    List<Job> findByTitleContainingIgnoreCase(String country);

    List<Job> findByKindJobAndWorkingMode(
            WorkingMode workingMode, KindJob kindJob);

    List<Job> findByWorkingModeAndTitleContainingIgnoreCase(
            WorkingMode workingMode, String title);

    List<Job> findByWorkingModeAndTitleContainingIgnoreCaseAndCountry(
            WorkingMode workingMode, String title, String country);

    List<Job> findByWorkingModeAndTitleContainingIgnoreCaseAndCity(
            WorkingMode workingMode, String title, String city);
}
