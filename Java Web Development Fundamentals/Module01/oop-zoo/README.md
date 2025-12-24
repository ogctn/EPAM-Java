# Object-oriented Programming basics

The aim of this exercise is for students to familiarize themselves with the basics of OOP (Object-oriented programming).

The structure of the domain classes allows the application to treat different class types in a uniform way,
that enables each child class to have its unique implementation for the same function.

### Description
The Application class contains information about a Zoo and its zookeepers and housed animals.
A zookeeper can feed animals that eat the food the zookeeper has. This means that if a zookeeper specialized in
food for herbivores, they can only give food to animals that have the same diet.

There are two zookeepers with different food specializations

| Zookeeper name | Consumption specialization |
|---|---|
| John | `Herbivore` |
| Jane | `Carnivore`, `Omnivore` |

The Zoo houses animals of 6 distinct types

| Animal | Consumption | Sound |
|---|---|---|
| Antelope | `Herbivore` | snorts |
| Hippo | `Herbivore` | barks |
| Lion | `Carnivore` | roars |
| Mandrill | `Omnivore` | screams |
| Rhino | `Herbivore` | moos |
| Zebra | `Herbivore` | brays |

Based on the previous table, it can be determined that John will be able to feed an Antelope,
and Jane can feed a Mandrill.

The task is to implement the Zoo's `feedtime()`, each animals' `makeSound()` and the Zookeeper's `feed()` methods 
to print out strings to a new line (`System.out.println()`) when the Zoo's feedtime method is called.
First, make sure that animal classes call their `makeSound()` method whenever being fed, and after that
the output log should contain which zookeeper (by its name) is feeding which animal.

### Example output

When John the zookeeper tries to give food to these animals -> [ Antelope, Hippo, Lion]

The output should be as follows:
```
Sage the Antelope snorts
John is feeding Sage the Antelope
Bubbles the Hippo barks
John is feeding Bubbles the Hippo
```
First, the log output contains info about the animal making a sound, and then gives more information
about which zookeeper is feeding which animal.

The lion should NOT be present, since John cannot give food to carnivores or omnivores.


### Example makeSound output
```
Sage the Antelope snorts
```
---
Please do not change method names, arguments, or return type of the methods.
