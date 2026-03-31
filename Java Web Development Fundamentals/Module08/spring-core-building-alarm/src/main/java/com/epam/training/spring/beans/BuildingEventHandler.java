package com.epam.training.spring.beans;

import com.epam.training.spring.model.Alarm;
import com.epam.training.spring.model.BuildingEvent;
import com.epam.training.spring.model.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BuildingEventHandler implements ApplicationListener<BuildingEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingEventHandler.class);
    private final AlarmHandler alarmHandler;

    public BuildingEventHandler(AlarmHandler alarmHandler) {
        this.alarmHandler = alarmHandler;
    }

    @Override
    public void onApplicationEvent(BuildingEvent event) {
        if (event.getEventType() == EventType.FIRE_DETECTION ||
                event.getEventType() == EventType.TEMPERATURE_TOO_HIGH) {

            String action = "Call Fire Department";
            if (event.getEventType() != EventType.FIRE_DETECTION)
                action = "Call Mechanic";

            String reason = "Fire detected";;
            if (event.getEventType() != EventType.FIRE_DETECTION)
                reason = "Room is hot";

            Alarm alarm = new Alarm(action, event.getLocation(), reason);

            LOGGER.info("Creating alarm for event: {}", event.getEventType());
            alarmHandler.handleAlarm(alarm);
        }
    }

}
