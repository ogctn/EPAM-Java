package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;
import com.epam.training.smarthome.controller.Mediator;

public class ChangeToWorkingDayCommand extends EventCommand {

    private final Mediator mediator;

    public ChangeToWorkingDayCommand(HomeController homeController) {
        super(homeController);
        mediator = homeController.getMediator();
    }

    @Override
    public void execute() {
        System.out.println("--> Change to working day event");
        mediator.changeCoffeeCreationStrategyToStrong();
        System.out.println();
    }

}
