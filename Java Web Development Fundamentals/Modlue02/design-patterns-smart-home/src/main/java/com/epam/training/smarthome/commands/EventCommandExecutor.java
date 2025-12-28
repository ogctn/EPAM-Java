package com.epam.training.smarthome.commands;

public class EventCommandExecutor {

    public void exec(EventCommand eventCommand) {
        eventCommand.execute();
    }

}
