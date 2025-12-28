package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public abstract class EventCommand {

    private final HomeController homeController;

    public EventCommand(HomeController homeController) {
        this.homeController = homeController;
    }

    public abstract void execute();
}
