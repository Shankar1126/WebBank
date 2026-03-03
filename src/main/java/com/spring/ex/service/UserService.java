package com.spring.ex.service;

import com.spring.ex.model.User;
import com.spring.ex.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User.UserBuilder;


import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void seedAdmin() {
        Optional<User> admin = userRepository.findByUsername("admin");
        if (admin.isEmpty()) {
            User u = new User("admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN");
            userRepository.save(u);
        }
    }


    public User createUser(String username, String rawPassword) {
        User u = new User(username, passwordEncoder.encode(rawPassword), "ROLE_USER");
        return userRepository.save(u);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.spring.ex.model.User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        return org.springframework.security.core.userdetails.User.withUsername(u.getUsername())
                .password(u.getPassword())
                .roles(u.getRole().replace("ROLE_", ""))
                .build();
    }
}
