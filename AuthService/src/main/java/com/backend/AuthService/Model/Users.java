package com.backend.AuthService.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean enable;
    @Column(nullable = false)
    private OffsetDateTime created_at;
    @Column(nullable = false)
    @OneToOne
    private Roles role;


}
