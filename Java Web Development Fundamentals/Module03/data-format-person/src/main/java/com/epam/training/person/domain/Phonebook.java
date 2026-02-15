package com.epam.training.person.domain;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "phonebook")
@XmlAccessorType(XmlAccessType.FIELD)
public class Phonebook {

    @XmlElement(name = "subscriber")
    private List<Subscriber> subscribers = new ArrayList<>();

    public Phonebook() {}

    public Phonebook(List<Subscriber> subscribers) {
        if (subscribers == null)
            this.subscribers = new ArrayList<>();
        else
            this.subscribers = new ArrayList<>(subscribers);
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        if (subscribers == null)
            this.subscribers = new ArrayList<>();
        else
            this.subscribers = subscribers;
    }
}
