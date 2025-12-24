package com.epam.training.food.service;

import com.epam.training.food.data.FileDataStore;
import com.epam.training.food.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DefaultFoodDeliveryService implements FoodDeliveryService {

    private final FileDataStore dataStore;

    public DefaultFoodDeliveryService(FileDataStore fileDataStore) {
        this.dataStore = fileDataStore;
    }

    @Override
    public Customer authenticate(Credentials credentials) throws AuthenticationException {
        return dataStore.getCustomers().stream()
                .filter(c -> c.getUserName().equals(credentials.getUserName()) &&
                        c.getPassword().equals(credentials.getPassword()))
                .findFirst()
                .orElseThrow(() -> new AuthenticationException("Credentials failed"));
    }

    @Override
    public List<Food> listAllFood() {
        return dataStore.getFoods();
    }


    private Optional<OrderItem> getItemInCart(Cart cart, Food food) {
        return cart.getOrderItems().stream()
                .filter(item -> item.getFood().getName().equals(food.getName()))
                .findFirst();
    }
    
    private void validateUCartArgs(Customer customer, Food food, int pieces) {
        if (pieces < 0)
            throw (new IllegalArgumentException("Pieces negative"));

        boolean exists = getItemInCart(customer.getCart(), food).isPresent();
        if (pieces == 0 && !exists)
            throw (new IllegalArgumentException("!"));
    }

    private void checkBalance(Customer customer, Food food, BigDecimal newItemPrice) {
        BigDecimal othersTotal = customer.getCart().getOrderItems().stream()
                .filter(item -> !item.getFood().getName().equals(food.getName()))
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (othersTotal.add(newItemPrice).compareTo(customer.getBalance()) > 0) {
            throw (new LowBalanceException("Unable to add current order for " + food + ", as with current cart content it would exceed available balance!"));
        }
    }

    private void recalculateCartTotal(Cart cart) {
        cart.setPrice(cart.getOrderItems().stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @Override
    public void updateCart(Customer customer, Food food, int pieces) throws LowBalanceException {
        validateUCartArgs(customer, food, pieces);

        Cart cart = customer.getCart();
        Optional<OrderItem> existing = getItemInCart(cart, food);

        if (pieces == 0) {
            cart.getOrderItems().remove(existing.get());
        } else {
            BigDecimal newItemPrice = food.getPrice().multiply(BigDecimal.valueOf(pieces));
            checkBalance(customer, food, newItemPrice);

            if (existing.isPresent()) {
                existing.get().setPieces(pieces);
                existing.get().setPrice(newItemPrice);
            } else {
                customer.getCart().getOrderItems().add(new OrderItem(food, pieces, newItemPrice));
            }
        }
        recalculateCartTotal(cart);
    }


    @Override
    public Order createOrder(Customer customer) throws IllegalStateException {
        Cart cart = customer.getCart();
        if (cart == null || cart.getOrderItems() == null || cart.getOrderItems().isEmpty())
            throw (new IllegalStateException("Empty card"));

        Order newOrder = new Order(customer);

        dataStore.createOrder(newOrder);

        customer.getOrders().add(newOrder);

        customer.setBalance(customer.getBalance().subtract(newOrder.getPrice()));

        cart.setOrderItems(new ArrayList<>());
        cart.setPrice(BigDecimal.ZERO);

        return (newOrder);
    }
}
