package com.backend.AuthService.Controller;

import com.backend.AuthService.Model.Dto.LoginRequest;
import com.backend.AuthService.Model.Dto.RegisterRequest;
import com.backend.AuthService.Model.Users;
import com.backend.AuthService.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest)
    {
        return authService.register(registerRequest);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest)
    {
        return authService.authenticateUser(loginRequest);
    }
}
