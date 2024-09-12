package com.example.estore.service.impl;
import com.example.estore.DTO.request.UserDTO;
import com.example.estore.model.User;
import com.example.estore.repository.UserRepository;
import com.example.estore.service.JwtService;
import com.example.estore.service.UserService;
import com.example.estore.utils.enums.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RequestContextFilter requestContextFilter;

    @Override
    public User register(UserDTO.RegisterDTO registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        return userRepository.save(user);
    }

    @Override
    public String login(UserDTO.LoginDTO loginRequest) {
        log.info("Request to login: {}", loginRequest);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials"));

        log.info("User logged in: {}", user.toString());

        return jwtService.generateToken(user);
    }

    @PostConstruct
    public void initAdmin() {
        Optional<User> cred = userRepository.findByUsername("admin");
        if (cred.isPresent()) {
            return;
        }

        String hashedPassword = passwordEncoder.encode("admin");
        User admin = User.builder()
                .username("admin")
                .password(hashedPassword)
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);
    }
}
