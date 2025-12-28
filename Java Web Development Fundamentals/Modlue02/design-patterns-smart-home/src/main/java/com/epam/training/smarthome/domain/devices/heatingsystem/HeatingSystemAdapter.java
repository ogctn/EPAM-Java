package com.epam.training.smarthome.domain.devices.heatingsystem;

import com.epam.training.smarthome.domain.observer.Observable;

public class HeatingSystemAdapter extends Observable implements HeatingSystem {

    private final LegacyHeatingSystem legacyHeatingSystem;

    public HeatingSystemAdapter() {
        legacyHeatingSystem = new LegacyHeatingSystem();
    }

    public HeatingSystemAdapter(LegacyHeatingSystem legacyHeatingSystem) {
        this.legacyHeatingSystem = legacyHeatingSystem;
    }

    @Override
    public void turnOn() {
        legacyHeatingSystem.turnOn();
        notifyObservers("[HeatingSystem] turn on");
    }

    @Override
    public void turnOff() {
        legacyHeatingSystem.turnOff();
        notifyObservers("[HeatingSystem] turn off");
    }

    @Override
    public boolean isTurnedOn() {
        return (legacyHeatingSystem.isTurnedOn());
    }
}
