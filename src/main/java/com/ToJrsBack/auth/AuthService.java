package com.ToJrsBack.auth;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ToJrsBack.company.Company;
import com.ToJrsBack.company.CompanyRepository;
import com.ToJrsBack.config.EmailService;
import com.ToJrsBack.junior.Junior;
import com.ToJrsBack.junior.JuniorRepository;
import com.ToJrsBack.security.JwtUtil;
import com.ToJrsBack.user.User;
import com.ToJrsBack.user.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JuniorRepository juniorRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered.");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords doesn't match.");
        }

        if (request.getPassword().length() < 6) {
            throw new RuntimeException("Password must have at least 6 characters.");
        }

        if (request.getRole().equalsIgnoreCase("JUNIOR")) {

            Junior junior = new Junior();
            junior.setEmail(request.getEmail());
            junior.setPassword(passwordEncoder.encode(request.getPassword()));
            junior.setName(request.getName());

            juniorRepository.save(junior);

            emailService.sendWelcomeEmail(junior.getEmail(), junior);


        } else if (request.getRole().equalsIgnoreCase("COMPANY")) {

            Company company = new Company();
            company.setEmail(request.getEmail());
            company.setPassword(passwordEncoder.encode(request.getPassword()));
            company.setName(request.getName());

            companyRepository.save(company);

        } else {
            throw new RuntimeException("Invalid role.");
        }
    }

    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email or Password incorrect.");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(token, user.getRole().name());
    }
}
