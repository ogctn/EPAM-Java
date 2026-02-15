package com.epam.training.person.domain;

public record Location(
        String iso3166,
        String country,
        String zip,
        String city
) {}