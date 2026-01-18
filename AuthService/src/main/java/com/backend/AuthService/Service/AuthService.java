package com.backend.AuthService.Service;

import com.backend.AuthService.Model.Dto.LoginRequest;
import com.backend.AuthService.Model.Dto.RegisterRequest;
import com.backend.AuthService.Model.Role;
import com.backend.AuthService.Model.Users;
import com.backend.AuthService.Repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder; // This will have instance of the BCryptPasswordEncoder(12) which u have explicitly mentioned in the SecurityConfig

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        Optional<Users> existingUser = usersRepo.findByEmail(registerRequest.getEmail());

        if(existingUser.isPresent())
        {
            return new ResponseEntity<String>("User already exists", HttpStatus.BAD_REQUEST);
        }
        else
        {
            Users user = new Users();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setCreated_at(OffsetDateTime.now()); //Set the time when the user is registered
            user.setRole(registerRequest.getRole()==null ? Role.USER :registerRequest.getRole());
            usersRepo.save(user);
        }
        return ResponseEntity.ok("User successfully registered");
    }

//     This Does the manual Username and Password validation does not involve the Spring Security

//    public ResponseEntity<String> login(LoginRequest loginRequest) {
//        Users user = usersRepo.findByEmail(loginRequest.getEmail())
//                .orElseThrow(()->new RuntimeException("Invalid credentials"));
//
//        if (!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword()))
//        {
//            return new ResponseEntity<String>("Invalid username or password",HttpStatus.BAD_REQUEST);
//        }
//
//        return ResponseEntity.ok("Logged in Successfully");
//    }

    //The below method uses Spring security to validated the credentials
    public ResponseEntity<String> authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        if(authentication.isAuthenticated())
            return new ResponseEntity<>(jwtService.generateToken(loginRequest.getEmail()),HttpStatus.OK);
        else
            return new ResponseEntity<String>("Check your credentials",HttpStatus.BAD_REQUEST);
    }
}
