package com.epam.training.smarthome.domain.devices.coffeemaker;

public interface CoffeeCreationStrategy {

    Integer getCaffeineInMg();

    CoffeeCreationStrategy StrongCoffee = () -> 40;
    CoffeeCreationStrategy WeakCoffee = () -> 20;

}
