package com.epam.training.person;

import com.epam.training.person.domain.Gender;
import com.epam.training.person.domain.Location;
import com.epam.training.person.domain.Person;
import com.epam.training.person.domain.Phone;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PersonFactory {

    public static List<Person> create() {
        Person john = new Person(
                UUID.fromString("2b1a8035-0aa3-4865-9f0f-5c3e46a11289"),
                "John Doe",
                LocalDate.of(1995, 7, 15),
                new Location("US", "United States", "12345", "New York"),
                Gender.MALE,
                List.of(new Phone("123", "456", "7890"),
                        new Phone("111", "111", "2222")));
        Person jane = new Person(
                UUID.fromString("d0b9301d-6720-4764-a527-8f12f4f99b1e"),
                "Jane Smith",
                LocalDate.of(1990, 1, 1),
                new Location("CA", "Canada", "V1X 2Y3", "Toronto"),
                Gender.FEMALE,
                List.of(new Phone("222", "222", "1111")));
        Person michael = new Person(
                UUID.fromString("c82a14c3-9309-4e6d-a2f3-7162f6dbb159"),
                "Michael Johnson",
                LocalDate.of(1978, 11, 5),
                new Location("GB", "United Kingdom", "SW1A 1AA", "London"),
                Gender.MALE,
                List.of(new Phone("333", "333", "1111"),
                        new Phone("333", "333", "2222")));
        return List.of(john, jane, michael);
    }
}
