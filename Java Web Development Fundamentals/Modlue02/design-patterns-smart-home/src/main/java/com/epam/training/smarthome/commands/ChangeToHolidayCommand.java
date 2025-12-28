package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;
import com.epam.training.smarthome.controller.Mediator;

public class ChangeToHolidayCommand extends EventCommand {

    private final Mediator mediator;

    public ChangeToHolidayCommand(HomeController homeController) {
        super(homeController);
        mediator = homeController.getMediator();
    }

    @Override
    public void execute() {
        System.out.println("--> Change to holiday event");
        mediator.changeCoffeeCreationStrategyToWeak();
        System.out.println();
    }

}
