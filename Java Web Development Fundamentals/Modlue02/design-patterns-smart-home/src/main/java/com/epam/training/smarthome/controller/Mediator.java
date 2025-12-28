package com.epam.training.smarthome.controller;

import com.epam.training.smarthome.domain.devices.AlarmSystem;
import com.epam.training.smarthome.domain.devices.FrontDoor;
import com.epam.training.smarthome.domain.devices.Light;
import com.epam.training.smarthome.domain.devices.coffeemaker.CoffeeMaker;
import com.epam.training.smarthome.domain.devices.coffeemaker.StrongCoffeeCreationStrategy;
import com.epam.training.smarthome.domain.devices.coffeemaker.WeakCoffeeCreationStrategy;
import com.epam.training.smarthome.domain.devices.heatingsystem.HeatingSystemAdapter;

public class Mediator {
    private final AlarmSystem alarmSystem;
    private final FrontDoor frontDoor;
    private final HeatingSystemAdapter heatingSystemAdapter;
    private final Light light;
    private final CoffeeMaker coffeeMaker;

    public Mediator(AlarmSystem alarmSystem, FrontDoor frontDoor, HeatingSystemAdapter heatingSystemAdapter,
                    Light light, CoffeeMaker coffeeMaker) {
         this.alarmSystem = alarmSystem;
         this.frontDoor = frontDoor;
         this.heatingSystemAdapter = heatingSystemAdapter;
         this.light = light;
         this.coffeeMaker = coffeeMaker;
    }


    public void chageAlarmSystemToOn() {
        if (alarmSystem.isTurnedOn())
            System.out.println("[HomeController] nothing to do (alarm system is already turned on)");
        else
            alarmSystem.turnOn();
    }

    public void chageAlarmSystemToOff() {
        if (alarmSystem.isTurnedOn())
            alarmSystem.turnOff();
        else
            System.out.println("[HomeController] nothing to do (alarm system is already turned off)");
    }

    public void alarmSystemAlarm() {
        if (alarmSystem.isTurnedOn())
            alarmSystem.alarm();
    }

    public void chageFrontDoorToOpen() {
        if (frontDoor.isOpen())
            System.out.println("[HomeController] nothing to do (front door is already opened)");
        else
            frontDoor.open();
    }

    public void chageFrontDoorToClose() {
        if (!frontDoor.isOpen())
            System.out.println("[HomeController] nothing to do (front door is already closed)");
        else
            frontDoor.close();
    }

    public void chageLightToOn() {
        if (light.isTurnedOn())
            System.out.println("[HomeController] nothing to do (light is already turned on)");
        else
            light.turnOn();
    }

    public void chageLightToOff() {
        if (!light.isTurnedOn())
            System.out.println("[HomeController] nothing to do (light is already turned off)");
        else
            light.turnOff();
    }

    public void chageHeatingSystemToOn() {
        if (heatingSystemAdapter.isTurnedOn())
            System.out.println("[HomeController] nothing to do (heating system is already turned on)");
        else
            heatingSystemAdapter.turnOn();
    }

    public void chageHeatingSystemToOff() {
        if (!heatingSystemAdapter.isTurnedOn())
            System.out.println("[HomeController] nothing to do (heating system is already turned off)");
        else
            heatingSystemAdapter.turnOff();
    }

    public void changeCoffeeCreationStrategyToWeak() {
        coffeeMaker.setCoffeeCreationStrategy(new WeakCoffeeCreationStrategy());
    }

    public void changeCoffeeCreationStrategyToStrong() {
        coffeeMaker.setCoffeeCreationStrategy(new StrongCoffeeCreationStrategy());
    }

    public void coffeeMakerCreateCoffee() {
        coffeeMaker.createCoffee();
    }
}
