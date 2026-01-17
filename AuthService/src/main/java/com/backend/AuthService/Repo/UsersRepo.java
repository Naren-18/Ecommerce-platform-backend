package com.backend.AuthService.Repo;

import com.backend.AuthService.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
}
