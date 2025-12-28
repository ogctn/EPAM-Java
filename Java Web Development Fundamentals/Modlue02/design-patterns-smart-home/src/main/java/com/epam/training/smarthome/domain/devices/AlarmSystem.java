package com.epam.training.smarthome.domain.devices;

import com.epam.training.smarthome.domain.observer.Observable;

public class AlarmSystem extends Observable {

    private boolean isTurnedOn;

    public AlarmSystem() {
        isTurnedOn = false;
    }

    public AlarmSystem(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public void alarm() {
        notifyObservers("[AlarmSystem] alarm");
        System.out.println("[AlarmSystem] alarm");
    }

    public boolean isTurnedOn() {
        return (this.isTurnedOn);
    }

    public void turnOff() {
        isTurnedOn = false;
        notifyObservers("[AlarmSystem] turn off");
        System.out.println("[AlarmSystem] turn off");
    }

    public void turnOn() {
        isTurnedOn = true;
        notifyObservers("[AlarmSystem] turn on");
        System.out.println("[AlarmSystem] turn on");
    }

}
