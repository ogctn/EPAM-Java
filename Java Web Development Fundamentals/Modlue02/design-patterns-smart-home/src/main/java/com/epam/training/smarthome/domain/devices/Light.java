package com.epam.training.smarthome.domain.devices;

import com.epam.training.smarthome.domain.observer.Observable;

public class Light extends Observable {

    private boolean isTurnedOn;

    public Light() {
        isTurnedOn = false;
    }

    public Light(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public boolean isTurnedOn() {
        return (this.isTurnedOn);
    }

    public void turnOff() {
        isTurnedOn = false;
        notifyObservers("[Light] turn off");
        System.out.println("[Light] turn off");
    }

    public void turnOn() {
        isTurnedOn = true;
        notifyObservers("[Light] turn on");
        System.out.println("[Light] turn on");
    }
}
