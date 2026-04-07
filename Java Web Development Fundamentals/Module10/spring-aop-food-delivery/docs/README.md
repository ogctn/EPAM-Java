# Introduction

In this exercise you will add method level logging and method execution time measurement with Spring AOP.

Take your **Spring Boot Food Delivery** solution, copy into **spring-aop-food-delivery** repository
and implement the aspects in that repository.

**AutoCode tester** is not defined for this exercise.

# Functionality

Add spring aop dependency to the application module:

    'org.springframework.boot:spring-boot-starter-aop'

Add the following cross-cutting concerns as Spring aspects to the application:

*   Method parameter value(s) logging before method execution.
*   Method return value logging after method execution.
*   Method execution time logging after method execution.

The service module of the template project already contains 3 annotations
that you can use as marker annotations and add to service methods to enable the aspects:

- `EnableArgumentLogging`
- `EnableReturnValueLogging`
- `EnableExecutionTimeLogging`

Create `LoggingAspect` class in application module, in the following package: `com.epam.training.food.aspect`  

    @Aspect
    @Component
    public class LoggingAspect {
        private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
    
        ...
    }

Rules for this class:

*   Do not inject any bean into the class (it is not needed to implement the solution).
*   The aspect should not depend on any food delivery related domain or service class.
*   The aspect should define 3 different methods for the three aspects.

Hint: argument logging method should be annotated:
    `@Before("@annotation(EnableArgumentLogging)")`

# Result

Example output:

    2022-03-18 14:29:32.638  INFO 16036 --- [           main] c.e.t.fooddelivery.aspect.LoggingAspect  : Method name: [authenticate], parameter(s): [User{email='a', password='a'}]
    2022-03-18 14:29:32.648  INFO 16036 --- [           main] c.e.t.fooddelivery.aspect.LoggingAspect  : Method name: [authenticate], return value: Customer{id=1, name='quicklogin', balance=100, orders=[], cart=Cart(orderItems=[], price=0)}
    2022-03-18 14:29:32.654  INFO 16036 --- [           main] c.e.t.fooddelivery.aspect.LoggingAspect  : Method name: [authenticate], execution time = 15906.9µs
    
    2022-03-18 14:29:32.654  INFO 16036 --- [           main] c.e.t.fooddelivery.aspect.LoggingAspect  : Method name: [listAllFood], return value: [Food{name='Fideua', calorie=558, description='Fideua', price=15, category=MEAL}, Food{name='Paella', calorie=379, description='Paella', price=13, category=MEAL}, Food{name='Tortilla', calorie=278, description='Tortilla', price=10, category=MEAL}, Food{name='Gazpacho', calorie=162, description='Gazpacho', price=8, category=MEAL}, Food{name='Quesadilla', calorie=470, description='Quesadilla', price=13, category=MEAL}]
    2022-03-18 14:29:32.656  INFO 16036 --- [           main] c.e.t.fooddelivery.aspect.LoggingAspect  : Method name: [authenticate], execution time = 2036.1µs
    
    2022-03-18 14:29:41.718  INFO 16036 --- [           main] c.e.t.fooddelivery.aspect.LoggingAspect  : Method name: [updateCart], parameter(s): [Customer{id=1, name='quicklogin', balance=100, orders=[], cart=Cart(orderItems=[], price=0)}, Food{name='Fideua', calorie=558, description='Fideua', price=15, category=MEAL}, 2]
    2022-03-18 14:29:41.720  INFO 16036 --- [           main] c.e.t.fooddelivery.aspect.LoggingAspect  : Method name: [authenticate], execution time = 1280.5µs

Notes
- The output of the domain objects can be different in your case, it depends on your implementation of `toString()` method.
- If you see '?' sign instead of μ in the console output, you can change the encoding in intelliJ to UTF-8 for the right output.
