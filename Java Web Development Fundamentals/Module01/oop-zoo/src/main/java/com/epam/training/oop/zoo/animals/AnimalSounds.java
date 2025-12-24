package com.epam.training.oop.zoo.animals;

import java.util.Map;

public final class AnimalSounds {

    private static final Map<Class<? extends Animal>, String> SOUNDS =
            Map.of(
                    Antelope.class, "snorts",
                    Hippo.class, "barks",
                    Lion.class, "roars",
                    Mandrill.class, "screams",
                    Rhino.class, "moos",
                    Zebra.class, "brays"
            );

    public AnimalSounds() {}

    public static String getSound(Class<? extends Animal> animalClass) {
        return (SOUNDS.get(animalClass));
    }
}
