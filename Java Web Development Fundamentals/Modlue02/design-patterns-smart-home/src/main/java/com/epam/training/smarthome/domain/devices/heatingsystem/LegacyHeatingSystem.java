package com.epam.training.smarthome.domain.devices.heatingsystem;

public class LegacyHeatingSystem {

    private boolean isTurnedOn;

    public LegacyHeatingSystem() {
        isTurnedOn = false;
    }

    public LegacyHeatingSystem(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public boolean isTurnedOn() {
        return (isTurnedOn);
    }

    public void turnOn() {
        this.isTurnedOn = true;
        System.out.println("[HeatingSystem] turn on");
    }

    public void turnOff() {
        this.isTurnedOn = false;
        System.out.println("[HeatingSystem] turn off");
    }

    public void opearate(boolean isTurnedOn) {
        if (isTurnedOn && !this.isTurnedOn)
                turnOn();
        else if (!isTurnedOn && this.isTurnedOn)
                turnOff();
    }

}
