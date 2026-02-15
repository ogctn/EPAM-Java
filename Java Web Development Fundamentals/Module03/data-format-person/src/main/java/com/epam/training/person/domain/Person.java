package com.epam.training.person.domain;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;

public record Person(
        UUID id,
        String name,
        LocalDate dateOfBirth,
        Location location,
        Gender gender,
        List<Phone> phones
) {}
