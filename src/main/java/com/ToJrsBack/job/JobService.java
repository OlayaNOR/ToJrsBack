package com.ToJrsBack.job;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ToJrsBack.company.Company;
import com.ToJrsBack.user.User;
import com.ToJrsBack.user.UserRepository;

@Service
public class JobService {

    private JobRepository repository;
    private UserRepository userRepository;

    public JobService(JobRepository repository, UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }
    
    public void create(JobRequest req){
        System.out.println(req);

        if (req.getTitle() == null || req.getTitle().isBlank()) {
            throw new RuntimeException("Title is mandatory");
        }

        if (req.getTitle().length() > 50) {
            throw new RuntimeException("Title is too large");
        }

        if (req.getDescription() == null || req.getDescription().isBlank()) {
            throw new RuntimeException("Description is mandatory");
        }

        if (req.getSkills() == null || req.getSkills().isBlank()) {
            throw new RuntimeException("Skills are mandatory");
        }

        if (req.getKindJob() == null) {
            throw new RuntimeException("Kind Job is mandatory");
        }

        if (req.getWorkingMode() == null) {
            throw new RuntimeException("Working mode is mandaroty");
        }

        if (req.getCountry() == null || req.getCountry().isBlank()) {
            throw new RuntimeException("Country is mandatory");
        }

        if (req.getCity() == null || req.getCity().isBlank()) {
            throw new RuntimeException("City is mandatory");
        }

        Job job = new Job();
        Company company = getAuthenticatedCompany();

        job.setCompany(company);
        job.setActive(true);
        job.setCity(req.getCity());
        job.setCountry(req.getCountry());
        job.setTitle(req.getTitle());
        job.setKindJob(req.getKindJob());
        job.setWorkingMode(req.getWorkingMode());
        job.setSkills(req.getSkills());
        job.setDescription(req.getDescription());
        repository.save(job);

    }

    public Company getAuthenticatedCompany() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!(user instanceof Company)) {
            throw new RuntimeException("El usuario no es una empresa");
        }

        return (Company) user;
    }

    public List<Job> filter(String country, String city, WorkingMode mode, String title, KindJob kindJob) {

        if (mode != null && title != null && city != null) {
            return repository.findByWorkingModeAndTitleContainingIgnoreCaseAndCity(mode, title, city);
        }

        if (mode != null && country != null && title != null) {
            return repository.findByWorkingModeAndTitleContainingIgnoreCaseAndCountry(mode, title, country);
        }

        if (mode != null && country != null) {
            return repository.findByWorkingModeAndCountryContainingIgnoreCase(mode, country);
        }

        if (mode != null && city != null) {
            return repository.findByWorkingModeAndCityContainingIgnoreCase(mode, city);
        }

        if (mode != null && kindJob != null) {
            return repository.findByKindJobAndWorkingMode(mode, kindJob);
        }

        if (mode != null && title != null) {
            return repository.findByWorkingModeAndTitleContainingIgnoreCase(mode, title);
        }

        if (mode != null) {
            return repository.findByWorkingMode(mode);
        }

        if (country != null) {
            return repository.findByCountryContainingIgnoreCase(country);
        }

        if (title != null) {
            return repository.findByTitleContainingIgnoreCase(title);
        }

        if (city != null) {
            return repository.findByCityContainingIgnoreCase(city);
        }

        return repository.findAll();
    }
}
