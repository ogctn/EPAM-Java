package com.epam.training.food.view;

import com.epam.training.food.domain.Credentials;
import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;
import com.epam.training.food.values.FoodSelection;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CLIView implements View{

    private final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public Credentials readCredentials() {
        System.out.print("Enter customer name:");
        String username = scanner.nextLine();
        System.out.print("Enter customer password:");
        String password = scanner.nextLine();
        return (new Credentials(username, password));
    }

    @Override
    public void printWelcomeMessage(Customer customer) {
        System.out.println("Welcome, " + customer.getName() + ". Your balance is: " + customer.getBalance() + " EUR.\n");
    }

    @Override
    public void printAllFoods(List<Food> foods) {
        System.out.println("Foods offered today:");
        foods.forEach(f -> System.out.println("- " + f.getName() + " " + f.getPrice() + " EUR each"));
        System.out.println();
    }

    @Override
    public FoodSelection readFoodSelection(List<Food> foods) {
        System.out.print("Please enter the name and amount of food (separated by comma) you would like to buy:");
        String input = scanner.nextLine();

        if (input == null || input.trim().isEmpty()) {
            return (FoodSelection.NONE);
        }

        try {
            String[] parts = input.split(",");
            String foodName = parts[0].trim();
            int amount = Integer.parseInt(parts[1].trim());

            return foods.stream()
                    .filter(f -> f.getName().equalsIgnoreCase(foodName))
                    .findFirst()
                    .map(f -> new FoodSelection(f, amount))
                    .orElse(FoodSelection.NONE);
        } catch (Exception e) {
            return (FoodSelection.NONE);
        }
    }

    @Override
    public void printAddedToCart(Food food, int pieces) {
        System.out.println("Added " + pieces + " piece(s) of " + food.getName() + " to the cart.\n");
    }

    @Override
    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printOrderCreatedStatement(Order order, BigDecimal balance) {
        String items = order.getOrderItems().stream()
                .map(i -> i.getFood().getName())
                .collect(Collectors.joining(", "));

        System.out.printf("Order (items: [%s], price: %s EUR, timestamp: %s) has been confirmed.%n",
                items,
                order.getPrice().toPlainString(),
                order.getTimestampCreated().format(DATE_TIME_FORMATTER));

        System.out.println("Your balance is " + balance.toPlainString() + " EUR.");
        System.out.println("Thank you for your purchase.");
    }
}
