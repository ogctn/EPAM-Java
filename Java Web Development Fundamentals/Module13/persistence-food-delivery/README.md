# Introduction

Your task in this exercise is to replace file based data store with relational database in Food Delivery application.

Food Delivery application will run an embedded H2 database. So there is no need to run external database.

The project file contains SQL insert statements to populate data in the database.

The business logic should remain the same, with small changes (e.g. use Spring Data repository interfaces instead of the FileDataStore)

# Changes

## Build

Add the following build dependencies to the **persistent** project

- org.springframework.boot:spring-boot-starter-data-jpa
- org.springframework.boot:spring-boot-starter-web
- com.h2database:h2 (runtime scope)

Notes:
- web dependency is required to manage H2 database via its web console.
- dependency versions are not needed - those are defined by spring boot

Add org.springframework.boot:spring-boot-starter-test to application module (tester needs it).

## persistence Module Code Changes

Define entities
- Make the domain model classes entities by adding annotations.
- `Credentials` class must be `@MappedSuperclass`, so that separate table for `Credentials` class will not be created.
- `Cart` is not persisted to the database, do not add `@Entity` annotation to it. `Customer`'s cart field should be `@Transient`.
- Since 'order' is a reserved keyword in SQL, the generated table name of the `Order` entity should be `_order` (use `@Table` annotation to define table name)
- In case of bidirectional relationships, do not forget to define on of the relations to be inverse.
  (In case of many-to-one relationship, the many side must be the owner)

Some model changes to be applied:
- Replace customerId with `Customer` reference in `Order` class.
- Add id field to `OrderItem` class.
- Add Order relation to `OrderItem`.
- Add id field to `Food` class.

Create Spring repository interfaces in `com.epam.training.food.repository` package.

- `CustomerRepository`
- `FoodRepository`
- `OrderRepository`

Notes:
- Each should extend `JpaRepository`, id type must be `Long`.
- Repository for `OrderItem` class is not needed. `OrderItems` will be managed by the owner `Order`.

Remove not necessary classes:

- Remove `FileDataStore` and all classes that are responsible for json data file handling.
- Remove csv files.

Data initialization
- Add **data.sql** file to **app/src/man/resources** folder, so that Spring Boot will execute the script on the database server.
- **Note:** The tester uses the same data.sql file to validate your solution. Please do not change its content.


## service Module Code Changes
- Add field for each repository interface in `DefaultOrderService`.
- Service methods must be `@Transactional`.
- The business logic should persist all the data in the database: use the repositories.
- authenticate(): do not load all customers into memory by calling findAll() method on the repository.
  Instead, define query method on the interface and load only the customer being authenticated.

## application Module Code Changes

Add the following configuration to **src/main/resources/application.properties**:

```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
    
spring.jpa.show-sql=true
spring.jpa.defer-datasource-initialization=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    
spring.h2.console.enabled=true
```

Notes:
- spring.datasource configurations contain the JDBC connection parameters
- H2 database management console can be accessed at the following address: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- The SQL statements that Hibernate executes will be visible in the log (see `spring.jpa.show-sql` configuration)

# Testing

Integration test class (`FoodDeliveryIntegrationTest`) is provided in the template project. Run the tests to validate your solution.

# Potential errors
  
- The domain model object refer each other (for example Customer - Order). The implemented equals(), hashcode(), and toString() methods might run infinite recursion.
  You will notice it by `StackOverflowError` in the log. You can solve the problem by excluding some fields.
- Your server might stop with an error: at can not insert the records into the database that are defined in data.sql file.
  Temporarily remove the lines, run your server, and check the structure of the generated tables in H2 console to understand the difference.
