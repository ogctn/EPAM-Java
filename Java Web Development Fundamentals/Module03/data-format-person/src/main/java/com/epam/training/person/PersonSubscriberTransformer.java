package com.epam.training.person;

import com.epam.training.person.domain.Person;
import com.epam.training.person.domain.Phone;
import com.epam.training.person.domain.Subscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PersonSubscriberTransformer implements Transformer<List<Person>, List<Subscriber>> {

    private final Predicate<Person> predicate;

    public PersonSubscriberTransformer(Predicate<Person> predicate) {
        if (predicate == null)
            throw new IllegalArgumentException();
        this.predicate = predicate;
    }

    @Override
    public List<Subscriber> transform(List<Person> persons) {
        if (persons == null || persons.isEmpty())
            return (List.of());

        List<Subscriber> result = new ArrayList<>();
        for (Person p : persons) {
            if (!predicate.test(p))
                continue;
            if (p.phones() == null)
                continue;
            for (Phone ph : p.phones())
                result.add(new Subscriber(format(ph), p.name()));
        }
        return (result);
    }

    private static String format(Phone phone) {
        return (phone.area() + "-" + phone.region() + "-" + phone.local());
    }
}
