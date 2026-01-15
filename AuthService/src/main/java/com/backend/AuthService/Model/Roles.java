package com.backend.AuthService.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String role;
    @OneToOne(mappedBy = "role")
    private Users users;
}
