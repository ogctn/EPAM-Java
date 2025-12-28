package com.epam.training.smarthome;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.epam.training.smarthome.commands.EventCommand;
import com.epam.training.smarthome.commands.EventCommandFactory;
import com.epam.training.smarthome.commands.EventCommandType;
import com.epam.training.smarthome.controller.HomeController;
import com.epam.training.smarthome.domain.devices.AlarmSystem;
import com.epam.training.smarthome.domain.devices.FrontDoor;
import com.epam.training.smarthome.domain.devices.Light;
import com.epam.training.smarthome.domain.devices.coffeemaker.CoffeeMaker;
import com.epam.training.smarthome.domain.devices.coffeemaker.WeakCoffeeCreationStrategy;
import com.epam.training.smarthome.domain.devices.heatingsystem.HeatingSystemAdapter;
import com.epam.training.smarthome.domain.devices.heatingsystem.LegacyHeatingSystem;
import com.epam.training.smarthome.domain.observer.MessageObserver;

@DisplayName("Test class for smart home design pattern home work")
public class SmartHomeIntegrationTest {

    private static final String FRONT_DOOR_OPEN_MESSAGE = "[FrontDoor] open";
    private static final String CREATE_STRONG_COFFEE_MESSAGE = "[CoffeeMaker] create coffee with 40mg caffeine";
    private static final String CREATE_WEAK_COFFEE_MESSAGE = "[CoffeeMaker] create coffee with 20mg caffeine";
    private static final String HEATING_SYSTEM_TURN_ON_MESSAGE = "[HeatingSystem] turn on";
    private static final String LIGHT_TURN_ON_MESSAGE = "[Light] turn on";
    private static final String COFFEE_MAKER_COFFEE_TYPE_CHANGED_MESSAGE = "[CoffeeMaker] change the type of coffee";
    private static final String ALARM_SYSTEM_TURN_OFF_MESSAGE = "[AlarmSystem] turn off";
    private static final String ALARM_SYSTEM_ALARM_MESSAGE = "[AlarmSystem] alarm";
    private MessageObserver messageObserver;

    @BeforeEach
    public void setUp() throws Exception {
        messageObserver = new MessageObserver();
    }

    @Test
    @DisplayName("Run ArrivesHomeEventCommand with default devices")
    public void testArrivesHomeEventCommandWithDefaultDevices() throws Exception {
        // GIVEN
        EventCommand arrivesHomeEventCommand = new EventCommandFactory(createDefaultHomeController())
            .createEventCommand(EventCommandType.ARRIVES_HOME);
        List<String> expectedMessages = List.of(FRONT_DOOR_OPEN_MESSAGE, CREATE_STRONG_COFFEE_MESSAGE);

        // WHEN
        arrivesHomeEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run ArrivesHomeEventCommand with default devices")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run ArrivesHomeEventCommand with turned on AlarmSystem, everything else default")
    public void testArrivesHomeEventCommandWithAlarmSystemTurnedOnEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .alarmSystem(createTurnedOnAlarmSystem())
            .build();
        EventCommand arrivesHomeEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.ARRIVES_HOME);
        List<String> expectedMessages = List.of(ALARM_SYSTEM_TURN_OFF_MESSAGE, FRONT_DOOR_OPEN_MESSAGE, CREATE_STRONG_COFFEE_MESSAGE);

        // WHEN
        arrivesHomeEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run ArrivesHomeEventCommand with"
                + " AlarmSystem turned on, everything else default")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run ArrivesHomeEventCommand with turned on AlarmSystem and opened FrontDoor, everything else default")
    public void testArrivesHomeEventCommandWithAlarmSystemTurnedOnAndFrontDoorOpenEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .alarmSystem(createTurnedOnAlarmSystem())
            .frontDoor(createOpenedFrontDoor())
            .build();
        EventCommand arrivesHomeEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.ARRIVES_HOME);
        List<String> expectedMessages = List.of(ALARM_SYSTEM_TURN_OFF_MESSAGE, CREATE_STRONG_COFFEE_MESSAGE);

        // WHEN
        arrivesHomeEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run ArrivesHomeEventCommand with"
                + " AlarmSystem turned on and FrontDoor opened, everything else default")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run ArrivesHomeEventCommand with turned on AlarmSystem and WeakCoffeeStrategy, everything else default")
    public void testArrivesHomeEventCommandWithAlarmSystemTurnedOnAndWeakCoffeeStrategyEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .alarmSystem(createTurnedOnAlarmSystem())
            .coffeeMaker(createCoffeeMakerWithWeakCoffeeCreationStrategy())
            .build();
        EventCommand arrivesHomeEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.ARRIVES_HOME);
        List<String> expectedMessages = List.of(ALARM_SYSTEM_TURN_OFF_MESSAGE, FRONT_DOOR_OPEN_MESSAGE, CREATE_WEAK_COFFEE_MESSAGE);

        // WHEN
        arrivesHomeEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run ArrivesHomeEventCommand with"
                + " AlarmSystem turned on and WeakCoffeeStrategy, everything else default")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run ArrivesHomeEventCommand with turned on AlarmSystem and opened FrontDoor and WeakCoffeeStrategy, everything else default")
    public void testArrivesHomeEventCommandWithAlarmSystemTurnedOnAndWeakCoffeeStrategyAndFrontDoorOpenEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .alarmSystem(createTurnedOnAlarmSystem())
            .coffeeMaker(createCoffeeMakerWithWeakCoffeeCreationStrategy())
            .frontDoor(createOpenedFrontDoor())
            .build();
        EventCommand arrivesHomeEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.ARRIVES_HOME);
        List<String> expectedMessages = List.of(ALARM_SYSTEM_TURN_OFF_MESSAGE, CREATE_WEAK_COFFEE_MESSAGE);

        // WHEN
        arrivesHomeEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run ArrivesHomeEventCommand with"
                + " AlarmSystem turned on and FrontDoor opened and WeakCoffeeStrategy, everything else default")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run ArrivesHomeEventCommand with opened FrontDoor, everything else default")
    public void testArrivesHomeEventCommandWithFrontDoorOpenEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .frontDoor(createOpenedFrontDoor())
            .build();
        EventCommand arrivesHomeEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.ARRIVES_HOME);
        List<String> expectedMessages = List.of(CREATE_STRONG_COFFEE_MESSAGE);

        // WHEN
        arrivesHomeEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run ArrivesHomeEventCommand with"
                + " FrontDoor opened, everything else default")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run ArrivesHomeEventCommand with opened FrontDoor and WeakCoffeeStrategy, everything else default")
    public void testArrivesHomeEventCommandWithFrontDoorOpenAndWeakCoffeeStrategyEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .frontDoor(createOpenedFrontDoor())
            .coffeeMaker(createCoffeeMakerWithWeakCoffeeCreationStrategy())
            .build();
        EventCommand arrivesHomeEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.ARRIVES_HOME);
        List<String> expectedMessages = List.of(CREATE_WEAK_COFFEE_MESSAGE);

        // WHEN
        arrivesHomeEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run ArrivesHomeEventCommand with"
                + " FrontDoor opened and WeakCoffeeStrategy, everything else default")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run ArrivesHomeEventCommand with WeakCoffeeStrategy, everything else default")
    public void testArrivesHomeEventCommandWithWeakCoffeeStrategyEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .coffeeMaker(createCoffeeMakerWithWeakCoffeeCreationStrategy())
            .build();
        EventCommand arrivesHomeEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.ARRIVES_HOME);
        List<String> expectedMessages = List.of(FRONT_DOOR_OPEN_MESSAGE, CREATE_WEAK_COFFEE_MESSAGE);

        // WHEN
        arrivesHomeEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run ArrivesHomeEventCommand with"
                + " WeakCoffeeStrategy, everything else default")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run GoingHomeEventCommand with default devices")
    public void testGoingHomeEventCommandWithDefaultDevices() throws Exception {
        // GIVEN
        EventCommand goingHomeEventCommand = new EventCommandFactory(createDefaultHomeController())
            .createEventCommand(EventCommandType.GOING_HOME);
        List<String> expectedMessages = List.of(HEATING_SYSTEM_TURN_ON_MESSAGE);

        // WHEN
        goingHomeEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run GoingHomeEventCommand with default devices")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run GoingHomeEventCommand with turned on HeatingSystem, everything else default")
    public void testGoingHomeEventCommandWithHeatingSystemTurnedOnEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .heatingSystemAdapter(new HeatingSystemAdapter(new LegacyHeatingSystem(true)))
            .build();
        EventCommand goingHomeEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.GOING_HOME);

        // WHEN
        goingHomeEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run GoingHomeEventCommand with"
                + " HeatingSystem turned on, everything else default")
            .isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    @DisplayName("Run MovementEventCommand with default devices")
    public void testMovementEventCommandWithDefaultDevices() throws Exception {
        // GIVEN
        EventCommand movementEventCommand = new EventCommandFactory(createDefaultHomeController())
            .createEventCommand(EventCommandType.MOVEMENT);
        List<String> expectedMessages = List.of(LIGHT_TURN_ON_MESSAGE);

        // WHEN
        movementEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run MovementEventCommand with default devices")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run MovementEventCommand with turned on AlarmSystem, everything else default")
    public void testMovementEventCommandWithAlarmSystemTurnedOnEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .alarmSystem(createTurnedOnAlarmSystem())
            .build();
        EventCommand movementEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.MOVEMENT);
        List<String> expectedMessages = List.of(ALARM_SYSTEM_ALARM_MESSAGE, LIGHT_TURN_ON_MESSAGE);

        // WHEN
        movementEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run MovementEventCommand with"
                + " AlarmSystem turned on, everything else default")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run MovementEventCommand with turned on Light, everything else default")
    public void testMovementEventCommandWithLightTurnedOnEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .light(createTurnedOnLight())
            .build();
        EventCommand movementEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.MOVEMENT);

        // WHEN
        movementEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run MovementEventCommand with"
                + " Light turned on, everything else default")
            .isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    @DisplayName("Run MovementEventCommand with turned on AlarmSystem and Light, everything else default")
    public void testMovementEventCommandWithLightTurnedOnAndAlarmSystemTurnedOnEverythingElseDefault() throws Exception {
        // GIVEN
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver)
            .light(createTurnedOnLight())
            .alarmSystem(createTurnedOnAlarmSystem())
            .build();
        EventCommand movementEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.MOVEMENT);
        List<String> expectedMessages = List.of(ALARM_SYSTEM_ALARM_MESSAGE);

        // WHEN
        movementEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run MovementEventCommand with"
                + " AlarmSystem turned on and Light turned on, everything else default")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run ChangeToHolidayEventCommand with default devices")
    public void testChangeToHolidayEventCommandWithDefaultDevices() throws Exception {
        // GIVEN
        HomeController homeController = createDefaultHomeController();
        EventCommand changeToHolidayEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.CHANGE_TO_HOLIDAY);
        List<String> expectedMessages = List.of(COFFEE_MAKER_COFFEE_TYPE_CHANGED_MESSAGE);

        // WHEN
        changeToHolidayEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run ChangeToHolidayEventCommand with default devices")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    @Test
    @DisplayName("Run ChangeToWorkingDayEventCommand with default devices")
    public void testChangeToWorkingDayEventCommandWithDefaultDevices() throws Exception {
        // GIVEN
        HomeController homeController = createDefaultHomeController();
        EventCommand changeToWorkingDayEventCommand = new EventCommandFactory(homeController)
            .createEventCommand(EventCommandType.CHANGE_TO_WORKING_DAY);
        List<String> expectedMessages = List.of(COFFEE_MAKER_COFFEE_TYPE_CHANGED_MESSAGE);

        // WHEN
        changeToWorkingDayEventCommand.execute();

        // THEN
        Assertions.assertThat(messageObserver.getMessages())
            .as("MessageObserver content after run ChangeToWorkingDayEventCommand with default devices")
            .containsExactlyInAnyOrderElementsOf(expectedMessages);
    }

    private HomeController createDefaultHomeController() {
        return new HomeController.HomeControllerBuilder(messageObserver).build();
    }

    private AlarmSystem createTurnedOnAlarmSystem() {
        return new AlarmSystem(true);
    }

    private FrontDoor createOpenedFrontDoor() {
        return new FrontDoor(true);
    }

    private CoffeeMaker createCoffeeMakerWithWeakCoffeeCreationStrategy() {
        return new CoffeeMaker(new WeakCoffeeCreationStrategy());
    }

    private Light createTurnedOnLight() {
        return new Light(true);
    }
}
