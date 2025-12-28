package com.epam.training.smarthome.domain.devices.coffeemaker;

public class WeakCoffeeCreationStrategy implements CoffeeCreationStrategy {

    private final Integer CAFFEINE_IN_MG = WeakCoffee.getCaffeineInMg();

    @Override
    public Integer getCaffeineInMg() {
        return (CAFFEINE_IN_MG);
    }

}
