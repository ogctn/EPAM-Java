package com.epam.training.food;

import com.epam.training.food.data.FileDataStore;
import com.epam.training.food.domain.*;
import com.epam.training.food.service.*;
import com.epam.training.food.view.*;
import com.epam.training.food.values.FoodSelection;

public class App {
    private final FoodDeliveryService service;
    private final View view;
    private final FileDataStore dataStore;

    public App(FoodDeliveryService service, View view, FileDataStore dataStore) {
        this.service = service;
        this.view = view;
        this.dataStore = dataStore;
    }

    public static void main(String[] args) {
        FileDataStore ds = new FileDataStore("test");
        ds.init();

        View cliView = new CLIView();
        FoodDeliveryService deliveryService = new DefaultFoodDeliveryService(ds);

        App app = new App(deliveryService, cliView, ds);
        app.run();
    }

    private Customer authentication() {
        Credentials namePass = view.readCredentials();
        Customer customer = service.authenticate(namePass);
        view.printWelcomeMessage(customer);
        return (customer);
    }

    private void displayMenu() {
        view.printAllFoods(service.listAllFood());
    }

    private void loop(Customer customer) {
        while (true) {
            FoodSelection selection = view.readFoodSelection(service.listAllFood());

            if (selection == FoodSelection.NONE) {
                if (customer.getCart().getOrderItems().isEmpty())
                    continue;
                break;
            }
            updateCart(customer, selection);
        }
    }

    private void updateCart(Customer customer, FoodSelection selection) {
        try {
            service.updateCart(customer, selection.food(), selection.amount());
            if (selection.amount() > 0) {
                view.printAddedToCart(selection.food(), selection.amount());
            }
        } catch (LowBalanceException | IllegalArgumentException e) {
            view.printErrorMessage(e.getMessage());
        }
    }

    private void printOrderCreated(Customer customer) {
        Order order = service.createOrder(customer);
        view.printOrderCreatedStatement(order, customer.getBalance());
        dataStore.writeOrders();
    }

    public void run() {
        try {
            Customer customer = authentication();
            if (customer == null)
                return;

            displayMenu();
            loop(customer);
            printOrderCreated(customer);

        } catch (AuthenticationException e) {
            view.printErrorMessage(e.getMessage());
        } catch (Exception e) {
            view.printErrorMessage("Error occurred: " + e.getMessage());
        }
    }

}

