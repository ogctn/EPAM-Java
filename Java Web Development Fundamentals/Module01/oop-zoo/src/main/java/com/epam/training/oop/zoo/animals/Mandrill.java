package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Mandrill extends Animal {
    public Mandrill(String name) {
        super(name, Consumption.OMNIVORES);
    }

    @Override
    public void makeSound() {
        System.out.println(
                getName() + " the " + getClass().getSimpleName() + " " +
                        AnimalSounds.getSound(getClass())
        );
    }
}
