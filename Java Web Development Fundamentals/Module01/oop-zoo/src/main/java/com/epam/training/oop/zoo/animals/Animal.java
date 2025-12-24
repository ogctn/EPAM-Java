package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public abstract class Animal {

    private final String name;
    private final Consumption consumption;

    public Animal(String name, Consumption consumption) {
        this.name = name;
        this.consumption = consumption;
    }

    public abstract void makeSound();

    public Consumption getConsumption() {
        return consumption;
    }


    public String getName() {
        return name;
    }
}
