package com.epam.training.smarthome.domain.devices.coffeemaker;

public class StrongCoffeeCreationStrategy implements CoffeeCreationStrategy {

    private final Integer CAFFEINE_IN_MG = StrongCoffee.getCaffeineInMg();

    @Override
    public Integer getCaffeineInMg() {
        return (CAFFEINE_IN_MG);
    }

}
