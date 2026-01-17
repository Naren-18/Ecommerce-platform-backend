package com.backend.AuthService.Service;

import com.backend.AuthService.Model.UserPrincipal;
import com.backend.AuthService.Model.Users;
import com.backend.AuthService.Repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        return new UserPrincipal(user);
    }
}
