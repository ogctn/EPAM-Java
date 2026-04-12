package com.epam.practice.jpa.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Test what happens if toString includes owner field
 */
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "owner")
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressLine;

    @ManyToOne
    private User owner;
}
