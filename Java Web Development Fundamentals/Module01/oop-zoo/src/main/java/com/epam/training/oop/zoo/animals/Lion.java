package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Lion extends Animal {
    public Lion(String name) {
        super(name, Consumption.CARNIVORES);
    }

    @Override
    public void makeSound() {
        System.out.println(
                getName() + " the " + getClass().getSimpleName() + " " +
                        AnimalSounds.getSound(getClass())
        );
    }
}
