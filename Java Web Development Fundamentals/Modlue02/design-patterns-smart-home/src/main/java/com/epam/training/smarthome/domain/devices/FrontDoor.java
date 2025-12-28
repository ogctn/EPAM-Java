package com.epam.training.smarthome.domain.devices;

import com.epam.training.smarthome.domain.observer.Observable;

public class FrontDoor extends Observable {

    private boolean isOpen;

    public FrontDoor() {
        isOpen = false;
    }

    public FrontDoor(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return (this.isOpen);
    }

    public void open() {
        isOpen = true;
        notifyObservers("[FrontDoor] open");
        System.out.println("[FrontDoor] open");
    }

    public void close() {
        isOpen = false;
        notifyObservers("[FrontDoor] close");
        System.out.println("[FrontDoor] close");
    }

}
