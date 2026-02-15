package com.epam.training.person;

import com.epam.training.person.domain.Person;
import java.time.LocalDate;
import java.util.function.Predicate;

public class PersonDateOfBirthPredicate implements Predicate<Person> {

    private final LocalDate threshold;

    public PersonDateOfBirthPredicate(LocalDate threshold) {
        if (threshold == null)
            throw new IllegalArgumentException();
        this.threshold = threshold;
    }

    @Override
    public boolean test(Person person) {
        if (person == null || person.dateOfBirth() == null)
            return false;
        return (person.dateOfBirth().isBefore(threshold));
    }
}
