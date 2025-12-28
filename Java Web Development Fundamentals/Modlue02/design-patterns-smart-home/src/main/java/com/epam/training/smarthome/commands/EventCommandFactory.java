package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EventCommandFactory {

    private final Map<EventCommandType, Supplier<EventCommand>> eventCommandMap = new HashMap<>();

    public EventCommandFactory(HomeController homeController) {
        eventCommandMap.put(EventCommandType.GOING_HOME, () -> new GoingHomeCommand(homeController));
        eventCommandMap.put(EventCommandType.ARRIVES_HOME, () -> new ArrivesHomeCommand(homeController));
        eventCommandMap.put(EventCommandType.MOVEMENT, () -> new MovementCommand(homeController));
        eventCommandMap.put(EventCommandType.CHANGE_TO_HOLIDAY, () -> new ChangeToHolidayCommand(homeController));
        eventCommandMap.put(EventCommandType.CHANGE_TO_WORKING_DAY, () -> new ChangeToWorkingDayCommand(homeController));
    }

    public EventCommand createEventCommand(EventCommandType type) {
        if (eventCommandMap.get(type) != null)
            return (eventCommandMap.get(type).get());
        else
            throw (new IllegalArgumentException());
    }

}
