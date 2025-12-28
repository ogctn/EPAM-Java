package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;
import com.epam.training.smarthome.controller.Mediator;

public class GoingHomeCommand extends EventCommand {

    private final Mediator mediator;

    public GoingHomeCommand(HomeController homeController) {
        super(homeController);
        mediator = homeController.getMediator();
    }

    @Override
    public void execute() {
        System.out.println("--> Going home event");
        mediator.chageHeatingSystemToOn();
        System.out.println();
    }

}
