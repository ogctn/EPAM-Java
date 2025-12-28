package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;
import com.epam.training.smarthome.controller.Mediator;

public class MovementCommand extends EventCommand {

    private final Mediator mediator;

    public MovementCommand(HomeController homeController) {
        super(homeController);
        mediator = homeController.getMediator();
    }

    @Override
    public void execute() {
        System.out.println("--> Movement event");
        mediator.alarmSystemAlarm();
        mediator.chageLightToOn();
        System.out.println();
    }
}
