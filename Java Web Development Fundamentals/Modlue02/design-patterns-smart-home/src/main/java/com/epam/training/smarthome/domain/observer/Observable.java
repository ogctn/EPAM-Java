package com.epam.training.smarthome.domain.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    protected void notifyObservers(String message) {
        observers.forEach(observer -> observer.update(message));
    }

}
