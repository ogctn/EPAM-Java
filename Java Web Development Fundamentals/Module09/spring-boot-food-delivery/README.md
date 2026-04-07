# Introduction

The task is to introduce Spring Boot in the Food Delivery project.

Take your solution from **Spring Core - Food Delivery** Exercise, and copy it into the new forked repository.


## Maven Build

Spring Boot Food Delivery parent pom should have the following parent:

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.13</version>
    </parent>

Remove dependencyManagement - spring-boot-starter-parent defines all dependencies which are needed.

In **application**, **service** and **persistence** modules remove non-project dependencies.

Add the following dependency to **application** module:

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
    </dependency>
		
Add the following dependency to **service** and **persistence** modules:

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>

Add spring boot maven plugin to application module (build / plugins section):

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

# Code changes

## ``SpringApplicationStarter`` class

It should be:

    @SpringBootApplication
    public class SpringApplicationStarter {
        public static void main(String[] args) {
            SpringApplication.run(SpringApplicationStarter.class);
        }
    }

## ``SpringConfig`` class

Delete the class
- application.properties file is the standard configuration file in Spring Boot, no need to add it via ``PropertySource``
- ``SpringBootApplication`` annotation already adds component scanning

## ``Application`` class

It should implement - ``CommandLineRunner`` interface. The signature of its ``run`` method changes,
the body should remain the same.
