package com.epam.training.smarthome.domain.observer;

import java.util.ArrayList;
import java.util.List;

public class MessageObserver implements Observer {

    private List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return (messages);
    }

    @Override
    public void update(String message) {
        this.messages.add(message);
    }
}
