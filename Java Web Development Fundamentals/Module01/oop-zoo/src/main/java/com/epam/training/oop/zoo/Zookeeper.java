package com.epam.training.oop.zoo;

import com.epam.training.oop.zoo.animals.Animal;

public class Zookeeper {
    private final String name;
    private final Consumption[] consumption;

    public Zookeeper(String name, Consumption... consumption) {
        this.name = name;
        this.consumption = consumption;
    }


    public void feed(Animal[] animals) {
        if (animals == null)
            return;

        for (Animal a : animals) {
            for (Consumption c : this.consumption) {
                if (a.getConsumption() == c) {
                    a.makeSound();
                    System.out.println(this.name + " is feeding " +
                            a.getName() + " the " + a.getClass().getSimpleName());
                    break;
                }
            }
        }
    }

    public String getName() {
        return name;
    }
}
