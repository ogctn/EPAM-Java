package com.epam.training.person;

import com.epam.training.person.domain.Subscriber;

import java.util.List;

public class SubscriberFactory {
    public static List<Subscriber> create() {
        return List.of(
                new Subscriber("123-456-7890", "John Doe"),
                new Subscriber("111-111-2222", "John Doe"),
                new Subscriber("222-222-1111", "Jane Smith"),
                new Subscriber("333-333-1111", "Michael Johnson"),
                new Subscriber("333-333-2222", "Michael Johnson")
        );
    }

    public static List<Subscriber> createNarrowedTo1990Jan1() {
        return List.of(
                new Subscriber("333-333-1111", "Michael Johnson"),
                new Subscriber("333-333-2222", "Michael Johnson")
        );
    }
}
