package com.spring.ex.Controller;

import com.spring.ex.dto.AuthRequest;
import com.spring.ex.dto.AuthResponse;
import com.spring.ex.model.User;
import com.spring.ex.security.JwtUtil;
import com.spring.ex.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest req) {
        try {
            User created = userService.createUser(req.getUsername(), req.getPassword());
            return ResponseEntity.ok(created.getUsername());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest req) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            // load user to get role
            var user = userService.loadUserByUsername(req.getUsername());
            String role = user.getAuthorities().stream().findFirst().map(a -> a.getAuthority()).orElse("ROLE_USER");
            String token = jwtUtil.generateToken(req.getUsername(), role);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}