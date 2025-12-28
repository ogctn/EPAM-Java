Introduction
============

Our customer builds smart homes for home owners. He sells devices and sensors, but has not got any software that control the devices. He asked us to build a **Smart Home Application** as detailed in the following.

Events
------

Here is the list of events that can happen in the smart home:

 
| Event name             | Description                                                                                                                                 |
|------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|
| **GoingHome**          | House owner is going home.  **Example:** the owner finishes his daily work, and signals with his cell phone that he/she is on the way home. |
| **ArrivesHome**        | The house owner arrives at the front door of the house. Some sensor detects that he/she is there.                                           |
| **Movement**           | Movement detected in the house.                                                                                                             |
| **ChangeToHoliday**    | This is a calendar or clock event: the day changes to holiday.                                                                              |
| **ChangeToWorkingDay** | This is a calendar or clock event: the day changes to working day.                                                                          |

Devices
-------

This is the list of devices that can be controlled in the smart home:

   
| Device Name       | Description                                                                               | Operations                   | Message                                            |
|-------------------|-------------------------------------------------------------------------------------------|------------------------------|----------------------------------------------------|
| **AlarmSystem**   | The alarm system can alarm with audio and visual effects on crime activity.               | Turn on.                     | \[AlarmSystem\] turn on                            |
|                   |                                                                                           | Turn off.                    | \[AlarmSystem\] turn off                           |
|                   |                                                                                           | Alarm.                       | \[AlarmSystem\] alarm                              |
| **HeatingSystem** | The heating system controls the temperature in the house.                                 | Turn on.                     | \[HeatingSystem\] turn on                          |
|                   |                                                                                           | Turn off.                    | \[HeatingSystem\] turn off                         |
| **FrontDoor**     | The front door of the house.                                                              | Open.                        | \[FrontDoor\] open                                 |
|                   |                                                                                           | Close.                       | \[FrontDoor\] close                                |
| **Light**         | The light of the house.                                                                   | Turn on.                     | \[Light\] turn on                                  |
|                   |                                                                                           | Turn off.                    | \[Light\] turn off                                 |
| **CoffeeMaker**   | Used to make coffee.  The coffee machine can work in two operation modes: weak or strong. | Create coffee.               | \[CoffeeMaker\] create coffe with **X**mg caffeine |
|                   |                                                                                           | Change coffee creation mode. | \[CoffeeMaker\] change the type of coffee          |

HomeController
--------------

HomeController is responsible to perform device actions for each event that can happen.

   
| Event                  | Device        | Operations (condition)                                 | Console log message                                                    |
|------------------------|---------------|--------------------------------------------------------|------------------------------------------------------------------------|
| **GoingHome**          | HeatingSystem | Turn on (if currently turned off)                      | \[HomeController\] nothing to do (heating system is already turned on) |
| **ArrivesHome**        | AlarmSystem   | Turn it off (if currently turned on)                   | \[HomeController\] nothing to do (alarm system is already turned off)  |
|                        | FrontDoor     | Open it (if currently closed)                          | \[HomeController\] nothing to do (front door is already opened)        |
|                        | CoffeeMaker   | Create coffee.                                         |                                                                        |
| **Movement**           | AlarmSystem   | Triggers alarm (if currently turned on)                |                                                                        |
|                        | Light         | Turn on (if currently turned off)                      | \[HomeController\] nothing to do (light is already turned on)          |
| **ChangeToHoliday**    | CoffeeMaker   | Change coffee creation mode to weak coffee creation.   |                                                                        |
| **ChangeToWorkingDay** | CoffeeMaker   | Change coffee creation mode to strong coffee creation. |                                                                        |

**Note**: Do not confuse HomeController's "nothing to do" messages with the messages the devices have. Simply log the HomeController's messages to the console when it has nothing to do.

Functionality
=============

The application does not require any user input. It  initializes the HomeController with devices in default states.

These default states are:

*   Light is turned off.
*   AlarmSystem is turned off.
*   HeatingSystem is turned off.
*   FrontDoor is closed.
*   CoffeeMaker mode: Strong Coffee Creation.

After the HomeController has been created, the application sends the following events to the HomeController.

*   ChangeToHoliday
*   GoingHome
*   Movement
*   ArrivesHome
*   Movement
*   ChangeToWorkingDay
*   GoingHome
*   ArrivesHome
*   Movement

Expected output:

    --> Change to holiday event
    [CoffeeMaker] change the type of coffee
    
    --> Going home event
    [HeatingSystem] turn on
    
    --> Movement event
    [Light] turn on
    
    --> Arrive home event
    [HomeController] nothing to do (alarm system is already turned off)
    [FrontDoor] open
    [CoffeeMaker] create coffee with 20mg caffeine
    
    --> Movement event
    [HomeController] nothing to do (light is already turned on)
    
    --> Change to working day event
    [CoffeeMaker] change the type of coffee
    
    --> Going home event
    [HomeController] nothing to do (heating system is already turned on)
    
    --> Arrive home event
    [HomeController] nothing to do (alarm system is already turned off)
    [HomeController] nothing to do (front door is already opened)
    [CoffeeMaker] create coffee with 40mg caffeine
    
    --> Movement event
    [HomeController] nothing to do (light is already turned on)
    
    all messages dispatched by the devices:
    [CoffeeMaker] change the type of coffee
    [HeatingSystem] turn on
    [Light] turn on
    [FrontDoor] open
    [CoffeeMaker] create coffee with 20mg caffeine
    [CoffeeMaker] change the type of coffee
    [CoffeeMaker] create coffee with 40mg caffeine

Design Patterns
===============

 
| Pattern  | Usage                                                                                                                                                                       |
|----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Strategy | Coffee maker has a parameter that controls how much coffee to create.  **Strategies:** Strong coffee strategy, weak coffee strategy.                                        |
| Observer | Each device is an Observable.  Observers has an update(String) method that the devices call with a message.                                                                 |
| Mediator | Home controller has device instances: HeatingSystem, AlarmSystem, etc.  Mediator defines a public method for each event to process.                                         |
| Factory  | **createEventCommand(eventType):** has an event enum parameter and creates an event command.                                                                                |
| Command  | Command class for each event.  Each command calls a method of the HomeController.                                                                                           |
| Builder  | HomeControllerBuilder to build HomeController and devices.                                                                                                                  |
| Adapter  | Third-party device LegacyHeatingSystem is given. **operate(boolean):** changes the isTurnedOn state to the given parameter. **isTurnedOn():** returns the isTurnedOn state. |

Application Classes
===================

Note that the following text was written primarily with Java in mind. When working with another language, use the appropriate conventions.

In case of C#:

*   Namespace and method names should be written in camel case, interface names should start with a capital I, etc.
*   Nested classes function a bit differently than in Java - pay attention to the notes given when implementing HomeController.

The implementation should contain the following classes:

![Devices](https://github.com/epam-java-cre/exercise-specification-images/blob/main/design-patterns-smart-home/base-devices.png?raw=true)
![Coffee](https://github.com/epam-java-cre/exercise-specification-images/blob/main/design-patterns-smart-home/coffee.png?raw=true)
![Heating System](https://github.com/epam-java-cre/exercise-specification-images/blob/main/design-patterns-smart-home/heating.png?raw=true)
![Observer](https://github.com/epam-java-cre/exercise-specification-images/blob/main/design-patterns-smart-home/observer.png?raw=true)
![Commands](https://github.com/epam-java-cre/exercise-specification-images/blob/main/design-patterns-smart-home/commands.png?raw=true)
![Home Controller](https://github.com/epam-java-cre/exercise-specification-images/blob/main/design-patterns-smart-home/home-controller.png?raw=true)


 
| Class                            | Description                                                                                                                                                                                                                                                                                                                                                     |
|----------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Application**                  | Entry point of the application.  The event commands can run here.  In C#, the entry point should be the Program class.                                                                                                                                                                                                                                          |
| **EventCommand**                 | Abstract base class for the commands.  execute() invokes the appropriate HomeController method.  There should be 5 classes which extend this class. The names of these classes are your choice.  The implementation of these classes can be similar, some code duplication is accepted.                                                                         |
| **EventCommandType**             | Enum for event command types.                                                                                                                                                                                                                                                                                                                                   |
| **EventCommandFactory**          | Factory to create EventCommand for each EventCommandType.                                                                                                                                                                                                                                                                                                       |
| **Observer**                     | Observer interface.                                                                                                                                                                                                                                                                                                                                             |
| **Observable**                   | Observable class, which is extended by every device.                                                                                                                                                                                                                                                                                                            |
| **MessageObserver**              | Implementation of Observer.  It **collects** the messages from Observables (devices).                                                                                                                                                                                                                                                                           |
| **Light**                        | Domain class, which represents the behaviour of the device.                                                                                                                                                                                                                                                                                                     |
| **AlarmSystem**                  | Domain class, which represents the behaviour of the device.                                                                                                                                                                                                                                                                                                     |
| **FrontDoor**                    | Domain class, which represents the behaviour of the device.                                                                                                                                                                                                                                                                                                     |
| **LegacyHeatingSystem**          | Legacy class provided by the Heating System. We decided to create an adapter to provide more convenient API.                                                                                                                                                                                                                                                    |
| **HeatingSystem**                | Convenient interface for Heating System.                                                                                                                                                                                                                                                                                                                        |
| **HeatingSystemAdapter**         | Adapter for the legacy heating system.                                                                                                                                                                                                                                                                                                                          |
| **CoffeeCreationStrategy**       | Interface for coffee creation strategies.                                                                                                                                                                                                                                                                                                                       |
| **StrongCoffeeCreationStrategy** | It is used on working days.                                                                                                                                                                                                                                                                                                                                     |
| **WeakCoffeeCreationStrategy**   | It is used on holidays.                                                                                                                                                                                                                                                                                                                                         |
| **CoffeeMaker**                  | Domain class, which represents the behaviour of coffee maker.  CoffeeMaker uses the CoffeeCreationStrategy to create coffee.                                                                                                                                                                                                                                    |
| **HomeController**               | Mediator class.  HomeController has an instance of each device.  HomeController defines a public method for each event type.                                                                                                                                                                                                                                    |
| **HomeControllerBuilder**        | Builder class, it builds the HomeController with the devices. **It is a static inner class of HomeController.**  In C#, this should be implemented differently - create a non-static inner builder class, and a parameterless private constructor for HomeController (it can be empty, its only purpose is to prevent instantiation without using the builder). |

Testing
=======

In order to satisfy the Application Tester, you have to make sure that:

*   Each device extends the Observable class.
    *   The test can observe devices and verify if the device sends the expected messages.
*   Command factory should create appropriate EventCommand for each event type.
*   HomeControllerBuilder
    *   new HomeController.HomeControllerBuilder(messageObserver).build() should create the HomeController with devices in default states.
    *   We can create HomeController with devices in other than default state. For example: new HomeController.HomeControllerBuilder(messageObserver).alarmSystem(new AlarmSystem(true)).build()

Constraints
===========

*   You can use general external libraries like lombok or logging, but not use libraries that would solve the problem instead of you - if in doubt, please ask your mentor.
*   Please do not use any additional techniques (like property file, etc.)
*   Print the messages to standard output (System.out)

Acceptance
==========

*   The application passes all test cases.
*   All Design Patterns are implemented as defined in the Design Patterns table and class diagram.
*   The application output is the expected output, see console output.

**Document Last Modification:** **2024.11.07. 10:24**
