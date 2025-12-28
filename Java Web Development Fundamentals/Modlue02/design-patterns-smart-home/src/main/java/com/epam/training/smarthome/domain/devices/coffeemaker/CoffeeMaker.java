package com.epam.training.smarthome.domain.devices.coffeemaker;

import com.epam.training.smarthome.domain.observer.Observable;

public class CoffeeMaker extends Observable {

    private CoffeeCreationStrategy coffeeCreationStrategy;

    public CoffeeMaker() {
        coffeeCreationStrategy = new StrongCoffeeCreationStrategy();
    };

    public CoffeeMaker(CoffeeCreationStrategy coffeeCreationStrategy) {
        this.coffeeCreationStrategy = coffeeCreationStrategy;
    }

    public void setCoffeeCreationStrategy(CoffeeCreationStrategy coffeeCreationStrategy) {
        this.coffeeCreationStrategy = coffeeCreationStrategy;
        notifyObservers("[CoffeeMaker] change the type of coffee");
        System.out.println("[CoffeeMaker] change the type of coffee");
    }

    public void createCoffee() {
        notifyObservers("[CoffeeMaker] create coffee with "
                + this.coffeeCreationStrategy.getCaffeineInMg() + "mg caffeine");
        System.out.println("[CoffeeMaker] create coffee with "
                + this.coffeeCreationStrategy.getCaffeineInMg() + "mg caffeine");
    }

}
