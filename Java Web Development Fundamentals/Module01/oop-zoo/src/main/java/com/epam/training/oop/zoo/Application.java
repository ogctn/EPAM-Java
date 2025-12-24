package com.epam.training.oop.zoo;

import com.epam.training.oop.zoo.animals.Animal;
import com.epam.training.oop.zoo.animals.Antelope;
import com.epam.training.oop.zoo.animals.Hippo;
import com.epam.training.oop.zoo.animals.Lion;
import com.epam.training.oop.zoo.animals.Mandrill;
import com.epam.training.oop.zoo.animals.Rhino;
import com.epam.training.oop.zoo.animals.Zebra;

public class Application {
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    private void run() {
        Zoo zoo = new Zoo(
                new Zookeeper[]{
                        new Zookeeper("John", Consumption.HERBIVORES),
                        new Zookeeper("Jane", Consumption.CARNIVORES, Consumption.OMNIVORES)},
                new Animal[]{
                        new Zebra("Stripes"), new Zebra("Shadow"), new Zebra("Zephir"),
                        new Antelope("Luna"), new Antelope("Thunder"), new Antelope("Sage"), new Antelope("Orion"),
                        new Hippo("Bubbles"), new Hippo("Tank"),
                        new Rhino("Brutus"), new Rhino("Avalanche"), new Rhino("Titan"),
                        new Lion("Leo"), new Lion("Nala"),
                        new Mandrill("Azibo"), new Mandrill("Kaya"), new Mandrill("Zuri"), new Mandrill("Simba")
                });
        zoo.feedtime();
    }
}
