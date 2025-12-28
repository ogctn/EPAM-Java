package com.epam.training.smarthome;

import com.epam.training.smarthome.commands.EventCommand;
import com.epam.training.smarthome.commands.EventCommandFactory;
import com.epam.training.smarthome.commands.EventCommandType;
import com.epam.training.smarthome.controller.HomeController;
import com.epam.training.smarthome.domain.observer.MessageObserver;

public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

    private void run() {

        MessageObserver messageObserver = new MessageObserver();
        messageObserver.update("all messages dispatched by the devices:");

        HomeController homeController = new HomeController
                .HomeControllerBuilder(messageObserver)
                .build();

        EventCommand arrivesHomeEventCommand = new EventCommandFactory(homeController)
                .createEventCommand(EventCommandType.ARRIVES_HOME);
        EventCommand goingHomeEventCommand = new EventCommandFactory(homeController)
                .createEventCommand(EventCommandType.GOING_HOME);
        EventCommand movementHomeEventCommand = new EventCommandFactory(homeController)
                .createEventCommand(EventCommandType.MOVEMENT);
        EventCommand changeToHolidayEventCommand = new EventCommandFactory(homeController)
                .createEventCommand(EventCommandType.CHANGE_TO_HOLIDAY);
        EventCommand changeToWorkingDayEventCommand = new EventCommandFactory(homeController)
                .createEventCommand(EventCommandType.CHANGE_TO_WORKING_DAY);

        changeToHolidayEventCommand.execute();
        goingHomeEventCommand.execute();
        movementHomeEventCommand.execute();
        arrivesHomeEventCommand.execute();
        movementHomeEventCommand.execute();
        changeToWorkingDayEventCommand.execute();
        goingHomeEventCommand.execute();
        arrivesHomeEventCommand.execute();
        movementHomeEventCommand.execute();

        messageObserver.getMessages().forEach(System.out::println);
    }
}
