package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;
import com.epam.training.smarthome.controller.Mediator;

public class ArrivesHomeCommand extends EventCommand {

    private final Mediator mediator;

    public ArrivesHomeCommand(HomeController homeController) {
        super(homeController);
        mediator = homeController.getMediator();
    }

    @Override
    public void execute() {
        System.out.println("--> Arrive home event");
        mediator.chageAlarmSystemToOff();
        mediator.chageFrontDoorToOpen();
        mediator.coffeeMakerCreateCoffee();
        System.out.println();
    }

}
