package com.epam.training.oop.zoo;

import com.epam.training.oop.zoo.animals.Animal;

public class Zoo {

    private final Zookeeper[] zookeepers;
    private final Animal[] animals;

    public Zoo(Zookeeper[] zookeepers, Animal[] animals) {
        this.zookeepers = zookeepers;
        this.animals = animals;
    }

    public void feedtime() {
        if (zookeepers == null || animals == null)
            return;

        for (Zookeeper z : zookeepers) {
            z.feed(this.animals);
        }
    }
}
