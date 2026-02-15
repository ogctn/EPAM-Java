package com.epam.training.person;

import com.epam.training.person.domain.Subscriber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class PersonSubscriberTransformerTest {

    @DisplayName("Transformation should narrow the list of five persons to two subscribers if date of birth is before of 1st Jan 1990")
    @Test
    public void transformTestIsBefore01Jan1990() {
        // Arrange
        PersonSubscriberTransformer underTest = new PersonSubscriberTransformer(
                new PersonDateOfBirthPredicate(LocalDate.of(1990, 1, 1))
        );
        List<Subscriber> expected = SubscriberFactory.createNarrowedTo1990Jan1();

        // Act
        List<Subscriber> actual = underTest.transform(PersonFactory.create());

        // Assert
        assertIterableEquals(expected, actual, "Lists do not match!");
    }

    @DisplayName("Transformation should return all the subscribers if date of birth is before of 1st Jan 2000")
    @Test
    public void transformTestIsBefore01Jan2000() {
        // Arrange
        PersonSubscriberTransformer underTest = new PersonSubscriberTransformer(
                new PersonDateOfBirthPredicate(LocalDate.of(2000, 1, 1))
        );
        List<Subscriber> expected = SubscriberFactory.create();

        // Act
        List<Subscriber> actual = underTest.transform(PersonFactory.create());

        // Assert
        assertIterableEquals(expected, actual, "Lists do not match!");
    }

    @DisplayName("Transformation should return empty list if date of birth is before of 1st Jan 1950")
    @Test
    public void transformTestIsBefore01Jan1950() {
        // Arrange
        PersonSubscriberTransformer underTest = new PersonSubscriberTransformer(
                new PersonDateOfBirthPredicate(LocalDate.of(1950, 1, 1))
        );
        List<Subscriber> expected = new ArrayList<>();

        // Act
        List<Subscriber> actual = underTest.transform(PersonFactory.create());

        // Assert
        assertIterableEquals(expected, actual, "Lists do not match!");
    }

}
