package com.epam.practice.jpa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String email;

    // Try fetch = FetchType.EAGER
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();
}
