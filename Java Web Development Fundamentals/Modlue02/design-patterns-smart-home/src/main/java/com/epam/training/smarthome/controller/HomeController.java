package com.epam.training.smarthome.controller;

import com.epam.training.smarthome.commands.*;
import com.epam.training.smarthome.domain.devices.AlarmSystem;
import com.epam.training.smarthome.domain.devices.coffeemaker.CoffeeMaker;
import com.epam.training.smarthome.domain.devices.FrontDoor;
import com.epam.training.smarthome.domain.devices.heatingsystem.HeatingSystemAdapter;
import com.epam.training.smarthome.domain.devices.Light;
import com.epam.training.smarthome.domain.observer.MessageObserver;
import com.epam.training.smarthome.domain.observer.Observable;
import com.epam.training.smarthome.domain.observer.Observer;

public class HomeController {

    private final Mediator mediator;
    private final EventCommandExecutor commandExecutor = new EventCommandExecutor();

    private HomeController(HomeControllerBuilder homeControllerBuilder) {
        initObservers(
                homeControllerBuilder.messageObserver,
                homeControllerBuilder.alarmSystem,
                homeControllerBuilder.frontDoor,
                homeControllerBuilder.heatingSystemAdapter,
                homeControllerBuilder.light,
                homeControllerBuilder.coffeeMaker);
        mediator = new Mediator(
                homeControllerBuilder.alarmSystem,
                homeControllerBuilder.frontDoor,
                homeControllerBuilder.heatingSystemAdapter,
                homeControllerBuilder.light,
                homeControllerBuilder.coffeeMaker);
    }

    private void initObservers(Observer messageObserver, Observable... observables) {
        for (Observable o : observables)
            o.addObserver(messageObserver);
    }

    public Mediator getMediator() { return (mediator); }

    public void onMovement() { commandExecutor.exec(new MovementCommand(this)); }
    public void onGoingHome() { commandExecutor.exec(new GoingHomeCommand(this)); }
    public void onArriveHome() { commandExecutor.exec(new ArrivesHomeCommand(this)); }
    public void onChangeToHoliday() { commandExecutor.exec(new ChangeToHolidayCommand(this)); }
    public void onChangeToWorkingDay() { commandExecutor.exec(new ChangeToWorkingDayCommand(this)); }



    public static class HomeControllerBuilder {

        private final Observer messageObserver;
        private AlarmSystem alarmSystem = new AlarmSystem();
        private FrontDoor frontDoor = new FrontDoor();
        private HeatingSystemAdapter heatingSystemAdapter = new HeatingSystemAdapter();
        private Light light = new Light();
        private CoffeeMaker coffeeMaker = new CoffeeMaker();

        public HomeControllerBuilder(MessageObserver messageObserver) {
            this.messageObserver = messageObserver;
        }

        public HomeControllerBuilder alarmSystem(AlarmSystem alarmSystem) {
            this.alarmSystem = alarmSystem;
            return (this);
        }

        public HomeControllerBuilder frontDoor(FrontDoor frontDoor) {
            this.frontDoor = frontDoor;
            return (this);
        }

        public HomeControllerBuilder heatingSystemAdapter(HeatingSystemAdapter heatingSystemAdapter) {
            this.heatingSystemAdapter = heatingSystemAdapter;
            return (this);
        }

        public HomeControllerBuilder light(Light light) {
            this.light = light;
            return (this);
        }

        public HomeControllerBuilder coffeeMaker(CoffeeMaker coffeeMaker) {
            this.coffeeMaker = coffeeMaker;
            return (this);
        }

        public HomeController build() {
            return (new HomeController(this));
        }
    }

}
